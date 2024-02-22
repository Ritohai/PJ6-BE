package backendshop.service.impl.user;

import backendshop.exception.customer.CustomersException;
import backendshop.model.dto.request.UserRequestLogin;
import backendshop.model.dto.request.UserRequestRegister;
import backendshop.model.dto.response.JwtReponse;

public interface UserService {
    JwtReponse handleLoging(UserRequestLogin userRequestLogin) throws CustomersException;
    String hanldeRegister(UserRequestRegister userRequestRegister) throws CustomersException;


}
