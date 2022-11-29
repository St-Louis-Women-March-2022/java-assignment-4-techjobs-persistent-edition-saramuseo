package org.launchcode.techjobs.persistent.controllers;

import org.launchcode.techjobs.persistent.models.Employer;
import org.launchcode.techjobs.persistent.models.Job;
import org.launchcode.techjobs.persistent.models.Skill;
import org.launchcode.techjobs.persistent.models.data.EmployerRepository;
import org.launchcode.techjobs.persistent.models.data.JobRepository;
import org.launchcode.techjobs.persistent.models.data.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * Created by LaunchCode
 */
@Controller
public class HomeController {

    @Autowired
    private EmployerRepository employerRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private SkillRepository skillRepository;


    @RequestMapping("")
    public String index(Model model) {

        model.addAttribute("title", "My Jobs");
        model.addAttribute("employers", employerRepository.findAll());
       model.addAttribute("skills", skillRepository.findAll());
        model.addAttribute("jobs", jobRepository.findAll());

        return "index";
    }

    @GetMapping("add")
    public String displayAddJobForm(Model model) {
        model.addAttribute("title", "Add Job");
        model.addAttribute(new Job());
        model.addAttribute("skills", skillRepository.findAll());
        model.addAttribute("employers", employerRepository.findAll());

        return "add";
    }

    @PostMapping("add")
    public String processAddJobForm(@ModelAttribute @Valid Job newJob,
                                       Errors errors, Model model, @RequestParam int employerId, @RequestParam List<Integer> skills) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Add Job");
            model.addAttribute(newJob);
            return "add";
        }
//minute 19.24 of video or 1.04 of krystal video
//        Employer employer = newJob.getEmployer();
//        Job job = newJob.get;
//        employerRepository.save(newEmployer);
//        Employer newEmployer = employerRepository.findById(employerId);
       // adding newEmployer to new job + look up add method, + request param within methods =
        model.addAttribute("title", "Add Job");
//        Optional <Employer> optEmployer = employerRepository.findById(employerId);
//        if (optEmployer.isEmpty()){
//            return "add";
//        }
//        Employer employer  = optEmployer.get();
       Employer employer = employerRepository.findById(employerId).orElse(new Employer());

        List<Skill> skillObjs = (List<Skill>) skillRepository.findAllById(skills);
        newJob.setSkills(skillObjs);
       newJob.setEmployer(employer);
        jobRepository.save(newJob);

        return "redirect:";
    }

    @GetMapping("view/{jobId}")
    public String displayViewJob(Model model, @PathVariable int jobId) {
        Optional<Job> optJob = jobRepository.findById(jobId);
        //Optional optEmployer = null;
        if (optJob.isPresent()) {
            Job job = (Job) optJob.get();
            model.addAttribute("job", job);
            model.addAttribute("title", "${'Job: ' + job.name}");
            return "view";
        } else {
            return "redirect:../";
        }
    }


}
