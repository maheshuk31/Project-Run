package com.example.lee.projectrun;


public class UserInformation {
    public String UniqueCode;
    public String FirstName;
    public String Image;
    public String LastName;
    public String Email;
    public String password;
    public String Age;
    public String Gender;
    public String TeachingLanguage;
    public String PracticeLanguage;
    public String PersonalInterests;
    public String Friends;

    public UserInformation(String UniqueCode, String FirstName,
                           String Image,
                           String LastName,
                           String Email,
                           String password,
                           String Age,
                           String Gender,
                           String TeachingLanguage,
                           String PracticeLanguage,
                           String PersonalInterests,
                           String Friends) {
        this.UniqueCode = UniqueCode;
        this.FirstName = FirstName;
        this.Image = Image;
        this.LastName = LastName;
        this.Email = Email;
        this.password = password;
        this.Age = Age;
        this.Gender = Gender;
        this.TeachingLanguage = TeachingLanguage;
        this.PracticeLanguage = PracticeLanguage;
        this.PersonalInterests = PersonalInterests;
        this.Friends = Friends;

        System.out.println(UniqueCode);

    }


}
