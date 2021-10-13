package com.example.Security.Security.controllers;


import java.security.Principal;
import java.util.*;

import com.example.Security.Security.models.*;
import com.example.Security.Security.repositories.*;
import com.example.Security.Security.utils.EncrytedPasswordUtils;
import com.example.Security.Security.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

@Controller
public class MainController {

    ArrayDeque<UserQuestion> appQuestionsQueue = new ArrayDeque<>();

    @Autowired
    IUserRepository userRepository;
    @Autowired
    IQuestionRepository questionRepository;
    @Autowired
    IUserQuestionRepository userQuestionRepository;
    @Autowired
    IRoleRepository roleRepository;
    @Autowired
    IUserRoleRepository userRoleRepository;

    @Autowired
    private EntityManager entityManager;


    @RequestMapping("/IwantBecomeAdmin")
    public String UserToAdmin() {
        String userName = getCurrentUser();
        AppUser appUser = userRepository.getUserByUserName(userName);
        AppRole userRole = roleRepository.getAppRoleByRoleName("ROLE_Admin");
        UserRole userRol = userRoleRepository.getUserRoleByAppUser(appUser);
        userRol.setAppRole(userRole);


        userRoleRepository.save(userRol);
        return "redirect:/logout";
    }


    @RequestMapping(value = {"/", "/welcome"}, method = RequestMethod.GET)
    public String welcomePage(Model model, HttpServletRequest httpRequest) {
        model.addAttribute("title", "Welcome");
        // model.addAttribute("message", "This is welcome page!");

        /*List<AppUser> users = (List<AppUser>) userRepository.findAll();
        System.out.println("Answer : "+ CheckIsAccept(users.stream().findFirst().get()));*/

//PROVERYAET NA ROLI
        //System.out.println(httpRequest.isUserInRole("ROLE_SuperAdmin"));


       /* AppRole user = new AppRole("User");
        AppRole admin = new AppRole("Admin");
        AppRole superAdmin = new AppRole("SuperAdmin");
        AppRole superAdmin = new AppRole("SuperAdmin");

        roleRepository.save(superAdmin);
        roleRepository.save(admin);
        roleRepository.save(user);*/

       /* AppRole superAdmin = new AppRole("ROLE_ADMIN");
        roleRepository.save(superAdmin);*/

 /*AppRole superAdmin = new AppRole("ROLE_SuperAdmin");
        roleRepository.save(superAdmin);*/

       /* AppRole superAdmin = new AppRole("ROLE_User");
        roleRepository.save(superAdmin);*/

        for (AppRole role : roleRepository.findAll()) {
            System.out.println(role.toString());
        }

        /*for (String name:appRoleDAO.getRoleNames(new Long(7))) {
            System.out.println("\n!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            System.out.println(name);

            System.out.println("\n!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        }*/

     /*  AppUser appUser = userRepository.findById((long)3).get();
       AppRole userRole = roleRepository.findById((long)1).get();
        UserRole userRol = new UserRole(appUser,userRole);
        userRoleRepository.save(userRol);*/
        return "welcomePage";
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String adminPage(Model model, Principal principal, HttpServletRequest httpRequest) {
        updateQueue();
        User loginedUser = (User) ((Authentication) principal).getPrincipal();

        String userInfo = WebUtils.toString(loginedUser);
        model.addAttribute("userInfo", userInfo); ;

        List<AppUser> users = new ArrayList<>();
        if (httpRequest.isUserInRole("ROLE_SUPERADMIN")) {
            users = (List<AppUser>) userRepository.findAll();
        } else {
            users = sortingRole("ROLE_USER");
        }
        List<UserQuestion> questions = new ArrayList<>();

        for (UserQuestion question : appQuestionsQueue
        ) {
            questions.add(question);
        }
        System.out.println(questions);
        model.addAttribute("users", users);
        model.addAttribute("questions", questions);

        System.out.println("!!!!!!!!!\n!!!!!!!!!!\n!!!!!!!!!!!!\n");


      /*  System.out.println("Name = " + getCurrentUser());
        System.out.println("Get id by name = "+ userRepository.getUserByUserName(getCurrentUser()).getUserId());
        System.out.println("Get size question = " + userQuestionRepository
                .findUserQuestionsByWorkerIdAndIsAnsweredNotNull(userRepository.getUserByUserName(getCurrentUser())));*/
        List<UserQuestion> list = (List<UserQuestion>) userQuestionRepository
                .findUserQuestionsByWorkerIdAndIsAnsweredNotNull(userRepository.getUserByUserName(getCurrentUser()));

        double avg = getAvgMark(list);

        model.addAttribute("avgMark", avg);

       // ??????????????????????????????????????

        return "adminPage";
    }

    public double getAvgMark( List<UserQuestion> list){
        double sum = 0 ;
            int count = 0;
        for (UserQuestion user:list) {
            if (user.getMark()!=null)   {
                sum+=user.getMark();
                count++;
            }
        }
        System.out.println("All size : "+ list.size());
        return sum/ count;
    }


    //UserRole

    public List<AppUser> sortingRole(String role) {
        List<AppUser> users = new ArrayList<>();
        for (UserRole userRole : userRoleRepository.findAll()) {
            if (userRole.getAppRole().getRoleName().equals(role)) {
                users.add(userRole.getAppUser());
            }
        }

        return users;
    }


    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(@RequestParam String name, @RequestParam String password, Model model) {


        try {
            System.out.println("error 1 Name : " + name + "\nPassword : " + password);

            password = EncrytedPasswordUtils.encrytePassword(password);


            AppUser appUser = new AppUser(name, password, true);
            AppRole userRole = roleRepository.getAppRoleByRoleName("ROLE_User");
            //if (userRepository.count()==0)

            UserRole userRol = new UserRole(appUser, userRole);


            userRepository.save(appUser);
            userRoleRepository.save(userRol);
            System.out.println("error 1 Name : " + name + "\nPassword : " + password);
        }catch (Exception ex){

        }
        return "loginPage";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String edit(@RequestParam Long id, @RequestParam String password, Model model) {


        password = EncrytedPasswordUtils.encrytePassword(password);
        AppUser appUser = userRepository.getByUserId(id);

        appUser.setEncrytedPassword(password);
        userRepository.save(appUser);

        return "adminPage";
    }

    @RequestMapping(value = "/login")
    public String loginPage(Model model) {

        return "loginPage";
    }

    @RequestMapping(value = "/logoutSuccessful", method = RequestMethod.GET)
    public String logoutSuccessfulPage(Model model) {
        model.addAttribute("title", "Logout");
        return "logoutSuccessfulPage";
    }

    @RequestMapping(value = "/userInfo", method = RequestMethod.GET)
    public String userInfo(Model model, Principal principal, HttpServletRequest request) {


        String currentUser = getCurrentUser();
        AppUser user = userRepository.getUserByUserName(currentUser);
        UserQuestion usQues = userQuestionRepository.findUserQuestionByWorkerIdNotNullAndAppUserAndIsAnswered(user, false);


        List<UserQuestion> questions = (List<UserQuestion>) userQuestionRepository.findUserQuestionsByAppUserAndIsAnsweredIsTrue(userRepository.getUserByUserName(getCurrentUser()));

        if (questions!=null){
            model.addAttribute("questions",questions);
        }

        // String newUser = userQuestionRepository.findUserQuestionByWorkerIdNotNullAndAppUserAndIsAnswered(user,false).getWorkerId().getUserName();

        if (usQues != null) {
            model.addAttribute("message", usQues.getWorkerId().getUserName());
        } else {
            model.addAttribute("message", "User is null");
        }
        // After user login successfully.
        String userName = principal.getName();

        System.out.println("User Name: " + userName);

        User loginedUser = (User) ((Authentication) principal).getPrincipal();

        String userInfo = WebUtils.toString(loginedUser);
        model.addAttribute("userInfo", userInfo);


        AppUser appUser = userRepository.getUserByUserName(getCurrentUser());
        UserRole userRole = userRoleRepository.getUserRoleByAppUser(appUser);
        if (userRole.getAppRole().getRoleName().equals("ROLE_User")) {
            model.addAttribute("isUser", userRole.getAppRole().getRoleName());
        }else{
            model.addAttribute("isUser", null);
        }




        return "userInfoPage";
    }




    @RequestMapping(value = "/saveMark", method = RequestMethod.POST)
    public String saveMark(@RequestParam Long questionId, @RequestParam Long mark, Model model) {
        System.out.println("Question = "+questionId );
        System.out.println("mark = "+mark );

        UserQuestion userQuestion =  userQuestionRepository.getById(questionId);
        userQuestion.setMark(mark);

        userQuestionRepository.save(userQuestion);
        return "userInfoPage";
    }



    @RequestMapping(value = "/403", method = RequestMethod.GET)
    public String accessDenied(Model model, Principal principal) {

        if (principal != null) {
            User loginedUser = (User) ((Authentication) principal).getPrincipal();

            String userInfo = WebUtils.toString(loginedUser);

            model.addAttribute("userInfo", userInfo);

            String message = "Hi " + principal.getName() //
                    + "<br> You do not have permission to access this page!";
            model.addAttribute("message", message);

        }

        return "403Page";
    }

    @RequestMapping(value = "/askQuestion", method = RequestMethod.POST)
    public String addToQueue(@RequestParam String question, Model model, HttpServletRequest httpRequest) {

        AppQuestion appQuestion = new AppQuestion(question);

        AppUser appUser = userRepository.getUserByUserName(getCurrentUser());

        if (appUser != null) {
            UserQuestion userQuestion = new UserQuestion(appUser, appQuestion, new Date(System.currentTimeMillis()), false, null);
            questionRepository.save(appQuestion);
            userQuestionRepository.save(userQuestion);

            appQuestionsQueue.addLast(userQuestion);

        }
        return "userInfoPage";
    }

    public String getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    public void updateQueue() {
        List<UserQuestion> appendToMainQueue = (List<UserQuestion>) userQuestionRepository.findUserQuestionByIsAnswered(false);
        for (UserQuestion qstSearch : appendToMainQueue
        ) {
            if (!searchInQueue(qstSearch)) {
                appQuestionsQueue.addLast(qstSearch);
            }
        }

    }

    public boolean searchInQueue(UserQuestion uq) {
        for (UserQuestion question : appQuestionsQueue
        ) {
            if (question.getAppQuestion().getId() == uq.getAppQuestion().getId()) {
                return true;
            }
        }
        return false;
    }

    @PostMapping("/acceptPerson")
    @ResponseBody
    public String acceptPerson() {

        UserQuestion userQues = userQuestionRepository.findUserQuestionByIsAnsweredAndWorkerId(false, userRepository.getUserByUserName(getCurrentUser()));


        if (userQues == null) {

            UserQuestion userQuestion = appQuestionsQueue.pollFirst();

          if (userQuestion !=null){
              String user = getCurrentUser();
              userQuestion.setWorkerId(userRepository.getUserByUserName(user));

              userQuestionRepository.save(userQuestion);
              return user;
          }
            return "Not question";
        } else {
            return "You have already accepted a user";
        }
        //List<UserQuestion> list = (List<UserQuestion>) userQuestionRepository.findUserQuestionsByWorkerIdNotNull();
        //return list.get(0).getAppUser().getUserName();
    }


    public Boolean CheckIsAccept(AppQuestion appQuestion){
        UserQuestion userQuestions =  userQuestionRepository.findUserQuestionByIsAnsweredAndAppUserAndWorkerIdNotNull(false,appQuestion);

        return userQuestions!=null;
    }



    @PostMapping("/endSession")
    @ResponseBody
    public String endSession() {

        UserQuestion userQues = userQuestionRepository.findUserQuestionByIsAnsweredAndWorkerId(false, userRepository.getUserByUserName(getCurrentUser()));

        if (userQues != null) {
            userQues.setAnswered(true);
            userQuestionRepository.save(userQues);
            appQuestionsQueue.pollFirst();
            return "Served the user " + userQues.getAppUser().getUserName() + " successfully";
        } else {
            return "You cannot end the session first start";
        }
        //List<UserQuestion> list = (List<UserQuestion>) userQuestionRepository.findUserQuestionsByWorkerIdNotNull();
        //return list.get(0).getAppUser().getUserName();
    }

    @RequestMapping(value = "/updateIsAnswered", method = RequestMethod.GET)
    public String updateQuestion(@RequestParam Long id, Model model) {
        UserQuestion uq = userQuestionRepository.findById(id).get();
        uq.setAnswered(true);

        appQuestionsQueue.remove(uq);

        userQuestionRepository.save(uq);
        return "welcomePage";
    }
}