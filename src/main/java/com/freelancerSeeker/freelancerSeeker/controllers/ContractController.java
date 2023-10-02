package com.freelancerSeeker.freelancerSeeker.controllers;

import com.freelancerSeeker.freelancerSeeker.Entity.ContractEntity;
import com.freelancerSeeker.freelancerSeeker.Enum.Role;
import com.freelancerSeeker.freelancerSeeker.Exceptions.ResourceNotFoundException;
import com.freelancerSeeker.freelancerSeeker.Entity.UserSiteEntity;
import com.freelancerSeeker.freelancerSeeker.Repository.ContractsRepository;
import com.freelancerSeeker.freelancerSeeker.Repository.UserSiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;


import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@Controller
public class ContractController {

    @Autowired
    ContractsRepository contractsRepo;

    @Autowired
    UserSiteRepository userSiteRepo;

    @PostMapping("/create-contract")
    public RedirectView createContract(Principal principal,String subject,String startDate,String endDate,double pricePerHour,String body){
        if (principal!=null){
            String username=principal.getName();
            UserSiteEntity userSite=userSiteRepo.findByUsername(username);
            if (userSite!=null&&userSite.getRoles()== Role.FREELANCER){
                ContractEntity contract=new ContractEntity(subject,startDate,endDate,pricePerHour,body,userSite);
                contract.setCreatedAt(LocalDate.now());
                contractsRepo.save(contract);
                return new RedirectView("/profile");
            }
        }

        return new RedirectView("/profile");
    }

    @GetMapping("/contracts")
    public String getAllContract(Model model){
        List<ContractEntity> contracts=contractsRepo.findAll();
        model.addAttribute("contracts",contracts);
        return "home.html";
    }

    @GetMapping("/contracts/{contractId}")
    public String getContractById(@PathVariable Long contractId,Model model){
        ContractEntity contract=contractsRepo.findById(contractId).orElseThrow(()->new ResourceNotFoundException());
        model.addAttribute("contractDetails",contract);
        return "contract.html";
    }

    @DeleteMapping("/contracts/{contractId}")
    public RedirectView deleteContract(@PathVariable Long contractId){
        contractsRepo.deleteById(contractId);
        return new RedirectView("/profile");
    }

    @PutMapping("/contracts/{contractId}")
    public RedirectView updatePost(@PathVariable Long contractId,String subject,String startDate,String endDate,double pricePerHour,String body){
        ContractEntity contract =contractsRepo.findById(contractId).orElseThrow(()->new ResourceNotFoundException());
        contract.setSubject(subject);
        contract.setStartDate(startDate);
        contract.setEndDate(endDate);
        contract.setPricePerHour(pricePerHour);
        contract.setBody(body);
        contractsRepo.save(contract);
        return new RedirectView("/contracts/"+contractId);
    }



}
