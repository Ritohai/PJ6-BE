package backendshop.audit;

import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class AuditAware implements AuditorAware <String> {
    @Override
    public Optional getCurrentAuditor() {
        return Optional.empty();
    }
}
