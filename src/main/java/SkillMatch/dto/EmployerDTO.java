package SkillMatch.dto;


public class EmployerDTO {
    private String  companyName;
    private String website;
    private String industry;

    public EmployerDTO(String companyName, String website, String industry) {
        this.companyName = companyName;
        this.website = website;
        this.industry = industry;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }
}
