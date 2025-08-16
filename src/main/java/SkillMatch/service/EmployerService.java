package SkillMatch.service;


import SkillMatch.exception.ResourceNotFoundException;
import SkillMatch.model.Candidate;
import SkillMatch.model.Employer;
import SkillMatch.model.Photo;
import SkillMatch.repository.EmployerRepo;
import SkillMatch.repository.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.io.*;

@Service
public class EmployerService {
    @Autowired
    private EmployerRepo repo;

    @Autowired
    private PhotoService photoService;

    @Autowired
    private PhotoRepository photoRepository;

    public List<Employer> getEmployers(){
       List<Employer>employers=repo.findAll();
       return employers;
    }

    public Employer getEmployerById(Long id){
        return repo.findById(id).orElseThrow(()->new ResourceNotFoundException("Employer not found"));
    }

    public Employer addEmployer(Employer employer){
        return repo.save(employer);
    }

    public void deleteEmployer(Long id){
       repo.deleteById(id);
    }
    public Employer UpdateEmployer(Long id,Employer employer){

        Employer currentData=repo.findById(id).orElseThrow(()->new ResourceNotFoundException("Employer not found"));
        currentData.setCompanyName(employer.getCompanyName());
        currentData.setDescription(employer.getDescription());
        currentData.setIndustry(employer.getIndustry());
        return currentData;

    }

    public ResponseEntity<?>uploadPhoto(MultipartFile file, Employer employer) throws IOException {
        try {
            Photo photo=photoService.createPhoto(file);
            photo.setEmployer(employer);
            photoRepository.save(photo);
            return ResponseEntity.ok().body("Image uploaded successfully");

        }catch (IOException  e){
            throw new IOException("Could not upload photo"+e.getMessage());
        }
    }
    public ResponseEntity<?> deletePhoto(Photo photo)throws IOException{
        photoService.deletePhoto(photo);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?>changeProfilePhoto(Photo photo, MultipartFile newPhoto)throws IOException{
        photoService.updatePhoto(photo.getUrl(),newPhoto);
        return ResponseEntity.ok().build();
    }
}
