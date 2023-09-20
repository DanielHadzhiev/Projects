package com.plannerapp.service.impl;
import com.plannerapp.model.entity.Priority;
import com.plannerapp.model.enums.PriorityNameEnum;
import com.plannerapp.repo.PriorityRepository;
import com.plannerapp.service.PriorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PriorityServiceImpl implements PriorityService {

    private PriorityRepository priorityRepository;

    @Autowired
    public PriorityServiceImpl(PriorityRepository priorityRepository) {
        this.priorityRepository = priorityRepository;
    }

    public void initializeDb(){
        if(!isDbInitialized()){

            Priority urgent = new Priority(PriorityNameEnum.URGENT,
                    "An urgent problem that blocks the system use until the issue is resolved.");
            Priority important = new Priority(PriorityNameEnum.IMPORTANT,
                    "A core functionality that your product is explicitly supposed to perform is compromised.");
            Priority low = new Priority(PriorityNameEnum.LOW,
                    "Should be fixed if time permits but can be postponed.");

            this.priorityRepository.saveAll(List.of(urgent,important,low));
        }
    }

    private boolean isDbInitialized(){
        return this.priorityRepository.count()>0;
    }
}
