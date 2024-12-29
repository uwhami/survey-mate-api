package com.surveymate.api.common.init;

import com.surveymate.api.common.code.entity.CommonCodeDtl;
import com.surveymate.api.common.code.entity.CommonCodeMst;
import com.surveymate.api.common.code.repository.CommonCodeMstRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Profile("dev")
@Component
public class DataInitializer implements CommandLineRunner {

    private final CommonCodeMstRepository commonCodeMstRepository;

    public DataInitializer(CommonCodeMstRepository commonCodeMstRepository) {
        this.commonCodeMstRepository = commonCodeMstRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        commonCodeMstRepository.deleteAll();
//        setRole();
    }

    void setRole(){
        CommonCodeMst roleGroup = new CommonCodeMst();
        roleGroup.setCodeGroup("ROLE");
        roleGroup.setDescription("Member roles group");
        roleGroup.setActive(true);

        CommonCodeDtl adminRole = new CommonCodeDtl();
        adminRole.setCodeValue("ROLE_ADMIN");
        adminRole.setCodeName("Administrator");
        adminRole.setActive(true);
        adminRole.setSortOrder(0);
        adminRole.setCodeMaster(roleGroup);

        CommonCodeDtl managerRole = new CommonCodeDtl();
        managerRole.setCodeValue("ROLE_MANAGER");
        managerRole.setCodeName("Manager");
        managerRole.setActive(true);
        managerRole.setSortOrder(1);
        managerRole.setCodeMaster(roleGroup);

        CommonCodeDtl userRole = new CommonCodeDtl();
        userRole.setCodeValue("ROLE_USER");
        userRole.setCodeName("General User");
        userRole.setActive(true);
        userRole.setSortOrder(2);
        userRole.setCodeMaster(roleGroup);

        roleGroup.setDetails(List.of(adminRole, managerRole, userRole));
        commonCodeMstRepository.save(roleGroup);
    }
}
