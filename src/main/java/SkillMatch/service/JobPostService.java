package SkillMatch.service;

import SkillMatch.dto.JobPostDTO;
import SkillMatch.model.Employer;
import SkillMatch.model.JobPost;
import SkillMatch.repository.JobPostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        return repo.findById(id).orElseThrow(()->new RuntimeException("Post Not Found"));
    }

    public List<JobPostDTO> searchPosts(String title){


        List<JobPost>posts=repo.findByTitleContainingIgnoreCase(title);
        List<JobPostDTO>dtos=new ArrayList<>();
        for (JobPost post:posts){

            Employer employer= post.getEmployer();
            dtos.add(new JobPostDTO(post.getTitle(), post.getEmployer()));
        }
        return dtos;
    }

    public JobPost updateJobPost(long id, JobPost newPost){
        JobPost oldPost=repo.findById(id).orElseThrow(()->new RuntimeException("Post not Found"));
        oldPost.setDescription(newPost.getDescription());
        oldPost.setEmployer(newPost.getEmployer());
        oldPost.setTitle(newPost.getTitle());
        oldPost.setRequiredSkills(newPost.getRequiredSkills());

        return repo.save(oldPost);
    }

    public JobPost deletePost(long id){
        JobPost post=repo.findById(id).orElseThrow(()->new RuntimeException("Post not found"));
        repo.deleteById(id);
        return post;
    }



}
