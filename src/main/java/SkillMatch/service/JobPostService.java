package SkillMatch.service;

import SkillMatch.exception.ResourceNotFoundException;
import SkillMatch.model.JobPost;
import SkillMatch.repository.JobPostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobPostService {

    @Autowired
    JobPostRepo repo;

    public JobPost addJob(JobPost jobPost){
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
        oldPost.setDescription(newPost.getDescription());
        oldPost.setEmployer(newPost.getEmployer());
        oldPost.setTitle(newPost.getTitle());
        oldPost.setRequiredSkills(newPost.getRequiredSkills());

        return repo.save(oldPost);
    }

    public JobPost deletePost(long id){
        JobPost post=repo.findById(id).orElseThrow(()->new ResourceNotFoundException("Job post not found"));
        repo.deleteById(id);
        return post;
    }



}
