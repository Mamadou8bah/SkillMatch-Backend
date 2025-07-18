package SkillMatch.controller;

import SkillMatch.dto.JobPostDTO;
import SkillMatch.model.JobPost;
import SkillMatch.service.JobPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post")
public class JobPostController {

    @Autowired
    JobPostService service;

    @GetMapping
    public List<JobPostDTO> getJobPost(@RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "5") int size){
        return service.getJobPost(page, size);
    }

    @PostMapping
    public JobPost addJob(@RequestBody JobPost jobPost){
        return service.addJob(jobPost);
    }



    @GetMapping("/{id}")
    public JobPost getJobPostById(@PathVariable long id){
        return service.getJobPostById(id);
    }

    @GetMapping("/search")
    public List<JobPostDTO> searchPosts(@RequestParam String title){
        return service.searchPosts(title);
    }


    @PutMapping("/{id}")
    public JobPost updateJobPost(@PathVariable long id,@RequestBody JobPost newPost){
       return service.updateJobPost(id,newPost);
    }

    @DeleteMapping("/{id}")
    public JobPost deletePost(long id){
       return service.deletePost(id);
    }
}
