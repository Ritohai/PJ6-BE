package backendshop.service.impl.brand;

import backendshop.exception.customer.CustomersException;
import backendshop.mapper.BrandMapper;
import backendshop.model.dto.BrandDTO;
import backendshop.model.entity.Brands;
import backendshop.repository.BrandRepository;
import org.springframework.stereotype.Service;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BrandServiceImpl implements BrandService{
    @Value("${path-upload}")
    private String pathUpload;
    //    @Value("${server.port}")
//    private Long port;
    @Autowired
    private BrandRepository brandRepository;


    public List<Brands> findAllShow(String brandName, String url, Integer startId, Integer endId, Integer page, Integer limit) {
//        Sort sort1 = Sort.by(filed);
        Sort sort = Sort.by(Sort.Direction.fromString("DESC"), "brandName");
        Pageable pageable = PageRequest.of(page, limit).withSort(sort);
        Page<Brands> brands = brandRepository.findAllBySearch(brandName, url,startId, endId, pageable);
        List<Brands> brandsList = new ArrayList<>();
        for (Brands brand : brands.getContent()) {
            if (!brand.isDeleteFlag()) {
                brandsList.add(brand);
            }
        }
        return brandsList;
    }

    @Override
    public BrandDTO findById(Long id) throws CustomersException {
        Optional<Brands> b = brandRepository.findById(id);
        if (b.isPresent() && b.get().isDeleteFlag()) {
            return BrandMapper.INSTANCE.brandToBrandDTO(b.get());
        }
        throw new CustomersException("Không tìm thấy thương hiệu");
    }

    @Override
    public BrandDTO addBrand(BrandDTO brandDTO) throws IOException {
        MultipartFile brandLogo = brandDTO.getBrandLogoFile();
        if (brandLogo != null && !brandLogo.isEmpty()) {
            String fileName = brandDTO.getBrandLogoFile().getOriginalFilename();
            FileCopyUtils.copy(brandDTO.getBrandLogoFile().getBytes(), new File(pathUpload + fileName));
            Brands brand = BrandMapper.INSTANCE.brandDTOToBrand(brandDTO);
            brand.setBrandLogo(fileName);
            Brands b = brandRepository.save(brand);
            return BrandMapper.INSTANCE.brandToBrandDTO(brand);
        } else {
            throw new RuntimeException("");
        }
    }

    @Override
    public BrandDTO updateBrand(BrandDTO brandDTO) throws CustomersException {
        try {
            Long brandId = brandDTO.getId();
            if (brandId == null) {
                throw new CustomersException("ID thương hiệu không được rỗng");
            }
            Optional<Brands> existingBrandOptional = brandRepository.findById(brandId);
            if (existingBrandOptional.isPresent()) {
                Brands existingBrand = existingBrandOptional.get();
                // Cập nhật các thuộc tính không phải file
                existingBrand.setBrandUrl(brandDTO.getBrandUrl());
                existingBrand.setBrandName(brandDTO.getBrandName());
                existingBrand.setStoreFlyer(brandDTO.getStoreFlyer());
                existingBrand.setMiniFlyer(brandDTO.getMiniFlyer());
                existingBrand.setUsageFlag(brandDTO.isUsageFlag());
                existingBrand.setDeleteFlag(brandDTO.isDeleteFlag());

                // Kiểm tra xem tệp logo thương hiệu mới có được cung cấp không
                MultipartFile newBrandLogo = brandDTO.getBrandLogoFile();
                if (newBrandLogo != null && !newBrandLogo.isEmpty()) {
                    // Xóa file logo thương hiệu cũ
                    File oldLogoFile = new File(pathUpload + existingBrand.getBrandLogo());
                    if (oldLogoFile.exists()) {
                        oldLogoFile.delete();
                    }
                    // Lưu file logo thương hiệu mới
                    String newFileName = newBrandLogo.getOriginalFilename();
                    FileCopyUtils.copy(newBrandLogo.getBytes(), new File(pathUpload + newFileName));
                    existingBrand.setBrandLogo(newFileName);
                }

                Brands updatedBrand = brandRepository.save(existingBrand);
                return BrandMapper.INSTANCE.brandToBrandDTO(updatedBrand);
            } else {
                throw new CustomersException("Thương hiệu không được tìm thấy hoặc bị xóa.");
            }
        } catch (IOException e) {
            throw new RuntimeException("Error updating brand: " + e.getMessage(), e);
        }
    }

    @Override
    public String delete(Long id) throws CustomersException {
        Optional<Brands> brand = brandRepository.findById(id);
        if (brand.isPresent() && brand.get().isDeleteFlag()) {
            Brands brand1 = brand.get();
            brand1.setDeleteFlag(false);
            brandRepository.save(brand1);
            return "Xóa thành công !";
        }
        throw new CustomersException("sản phẩm không tồn tại ");
    }

    //    File CSV
    @Override
    public void exportToCsv(HttpServletResponse response, List<Brands> data) throws IOException {
        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=data.csv");

        try (PrintWriter writer = response.getWriter();
             CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader("id", "brand_name", "brand_url", "brand_logo", "store_flyer", "mini_flyer", "usage_flag", "create_date", "update_date"))) {
            for (Brands row : data) {
                csvPrinter.printRecord(row.getId(), row.getBrandName(), row.getBrandLogo(), row.getBrandUrl(), row.getStoreFlyer(), row.getMiniFlyer(), row.isUsageFlag(), row.getCreateDate(), row.getUpdateDate());

            }
        }
    }

    public List<Brands> findAll() {
        return brandRepository.findAll();
    }
}
