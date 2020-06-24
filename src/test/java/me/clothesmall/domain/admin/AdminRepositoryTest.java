package me.clothesmall.domain.admin;

import me.clothesmall.domain.IsDeletedTypeEnum;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
public class AdminRepositoryTest {
    @Autowired
    private AdminRepository adminRepository;

    @Test
    public void findById() {
        Long AdminId = 1L;
        Admin admin = adminRepository.findById(AdminId).orElseGet(Admin::new);

        assertThat(admin.getIsDeleted()).isEqualTo(IsDeletedTypeEnum.N);
    }
}
