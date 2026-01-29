package SkillMatch.service;

import SkillMatch.exception.ResourceNotFoundException;
import SkillMatch.model.JobPost;
import SkillMatch.model.User;
import SkillMatch.repository.JobPostRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JobPostService {


    private final JobPostRepo repo;

    public JobPost addJob(JobPost jobPost){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        jobPost.setEmployer(user.getEmployer());
        return repo.save(jobPost);
    }
    public List<JobPost> getJobPost(int pageNo,int readCount){
        Pageable pageable= PageRequest.of(pageNo, readCount);
        Page<JobPost> page=repo.findAll(pageable);
        List<JobPost> posts=page.getContent();

        return posts;
    }

    public JobPost getJobPostById(long id){
        return repo.findById(id).orElseThrow(()->new ResourceNotFoundException("Job post not found"));
    }

    public List<JobPost> searchPosts(String title){
        List<JobPost>posts=repo.findByTitleContainingIgnoreCase(title);
        return posts;
    }

    public JobPost updateJobPost(long id, JobPost newPost){
        JobPost oldPost=repo.findById(id).orElseThrow(()->new ResourceNotFoundException("Job post not found"));
        if (newPost.getTitle() != null) {
            oldPost.setTitle(newPost.getTitle());
        }
        if (newPost.getDescription() != null) {
            oldPost.setDescription(newPost.getDescription());
        }
        if (newPost.getSalary() != 0) {
            oldPost.setSalary(newPost.getSalary());
        }
        if (newPost.getRequiredSkills() != null) {
            oldPost.setRequiredSkills(newPost.getRequiredSkills());
        }
        return repo.save(oldPost);
    }

    public JobPost deletePost(long id){
        JobPost post=repo.findById(id).orElseThrow(()->new ResourceNotFoundException("Job post not found"));
        repo.deleteById(id);
        return post;
    }



}
