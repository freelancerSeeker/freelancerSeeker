package com.freelancerSeeker.freelancerSeeker.controllers;

import com.freelancerSeeker.freelancerSeeker.Entity.ContractEntity;
import com.freelancerSeeker.freelancerSeeker.Enum.Role;
import com.freelancerSeeker.freelancerSeeker.Exceptions.ResourceNotFoundException;
import com.freelancerSeeker.freelancerSeeker.Entity.UserSiteEntity;
import com.freelancerSeeker.freelancerSeeker.Repository.ContractsRepository;
import com.freelancerSeeker.freelancerSeeker.Repository.UserSiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;


import java.security.Principal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class ContractController {

    @Autowired
    ContractsRepository contractsRepo;

    @Autowired
    UserSiteRepository userSiteRepo;


    @PostMapping("/create-contract")
    public RedirectView createContract(Principal principal, @RequestParam ("subject")String subject,
                                       @RequestParam ("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                                       @RequestParam (value = "endDate",required = true) @DateTimeFormat(pattern = "yyyy-MM-dd")Date endDate,
                                       @RequestParam ("pricePerHour")double pricePerHour, @RequestParam ("body")String body,
                                       @RequestParam ("approvedBy")String approvedBy){
        if (principal!=null){
            String username=principal.getName();
            UserSiteEntity userSite=userSiteRepo.findByUsername(username);
            if (userSite!=null&&userSite.getRoles()== Role.FREELANCER){
                ContractEntity contract=new ContractEntity();
                contract.setSubject(subject);
                contract.setStartDate(startDate);
                contract.setEndDate(endDate);
                contract.setPricePerHour(pricePerHour);
                contract.setBody(body);
                contract.setUser(userSite);
                contract.setCreatedAt(new Date());
                contract.setApprovedBy(userSiteRepo.findByUsername(approvedBy));
                contractsRepo.save(contract);
                return new RedirectView("/contracts");
            }
        }

        return new RedirectView("/contracts");
    }



    @PostMapping("/approve-contract/{contractId}")
    public RedirectView approveContract(@PathVariable long contractId, Principal principal) {
        if (principal != null) {
            String username = principal.getName();
            UserSiteEntity userSite = userSiteRepo.findByUsername(username);

            if (userSite != null && userSite.getRoles() == Role.USER) {
                ContractEntity contractEntity = contractsRepo.findById(contractId).orElseThrow(()->new ResourceNotFoundException());
                if (contractEntity!=null && contractEntity.getApprovedBy().getUsername().equals(username)) {
                    contractEntity.setApproved(true);
                    contractsRepo.save(contractEntity);
                    return new RedirectView("/contracts");
                }
            }
        }
        return new RedirectView("/contracts");
    }




    @GetMapping("/contracts")
    public String getAllContract(Principal principal,Model model){
        if(principal!=null){
            String username=principal.getName();
            UserSiteEntity userSite=userSiteRepo.findByUsername(username);
            model.addAttribute("username",username);
            if(userSite!=null&&userSite.getRoles()== Role.FREELANCER){
                List<ContractEntity> contractEntities=userSite.getContracts();
                model.addAttribute("contracts",contractEntities);
                model.addAttribute("user",userSite);
                return "contract";
            }else{
                List<ContractEntity> contractEntities=userSite.getContractsForApprove();
                model.addAttribute("contracts",contractEntities);
                model.addAttribute("user",userSite);
                return "contract";
            }
        }

        return "contract";
    }

    @DeleteMapping("/contracts/delete/{contractId}")
    public RedirectView deleteContract(Principal principal,@PathVariable Long contractId){
        if(principal!=null){
            String username=principal.getName();
            UserSiteEntity userSite=userSiteRepo.findByUsername(username);
            if(userSite!=null&&userSite.getRoles()== Role.FREELANCER){
                contractsRepo.deleteById(contractId);
                return new RedirectView("/contracts");
            }
        }

        return new RedirectView("/contracts");
    }

    @PutMapping("/contracts/update/{contractId}")
    public RedirectView updatePost(Principal principal,@PathVariable Long contractId, @RequestParam ("subject")String subject,
                                   @RequestParam ("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                                   @RequestParam (value = "endDate",required = true) @DateTimeFormat(pattern = "yyyy-MM-dd")Date endDate,
                                   @RequestParam ("pricePerHour")double pricePerHour, @RequestParam ("body")String body){
        if(principal!=null){
            String username=principal.getName();
            UserSiteEntity userSite=userSiteRepo.findByUsername(username);
            if(userSite!=null&&userSite.getRoles()== Role.FREELANCER){
                ContractEntity contract =contractsRepo.findById(contractId).orElseThrow(()->new ResourceNotFoundException());
                if(!contract.isApproved()){
                    contract.setSubject(subject);
                    contract.setStartDate(startDate);
                    contract.setEndDate(endDate);
                    contract.setPricePerHour(pricePerHour);
                    contract.setBody(body);
                    contractsRepo.save(contract);
                    return new RedirectView("/contracts");
                }
            }
        }

        return new RedirectView("/contracts");
    }


    @GetMapping("/contracts/{contractId}")
    public String getContractById(@PathVariable Long contractId,Model model){
        ContractEntity contract=contractsRepo.findById(contractId).orElseThrow(()->new ResourceNotFoundException());
        model.addAttribute("contractDetails",contract);
        return "contract";
    }





}
