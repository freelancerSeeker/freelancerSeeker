package com.freelancerSeeker.freelancerSeeker.controllers;

import com.freelancerSeeker.freelancerSeeker.Entity.PostsEntity;
import com.freelancerSeeker.freelancerSeeker.Enum.Role;
import com.freelancerSeeker.freelancerSeeker.Entity.UserSiteEntity;
import com.freelancerSeeker.freelancerSeeker.Repository.PostsRepository;
import com.freelancerSeeker.freelancerSeeker.Repository.UserSiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
public class UserAuthenticationController {
    // This controller to handle both freelancer and user authentication.

    @Autowired
    UserSiteRepository userSiteRepo;
    @Autowired
    HttpServletRequest request;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    PostsRepository postsRepo;


    @GetMapping("/login")
    public String login(Principal p, Model loginModel) {
        if (isAlreadyLoggedIn(p)) {
            return "redirect:/profile/" + p.getName();
        }
        loginModel.addAttribute("countries", allCountriesList);
        return "signup";
    }

    @GetMapping("/")
    public String getHome(Principal p, Model homeModel, @RequestParam(required = false, defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, 9);
        Page<PostsEntity> posts = postsRepo.findAllByOrderByCreatedAtDesc(pageable);
        homeModel.addAttribute("postList", posts.getContent());
        homeModel.addAttribute("Page", page);
        homeModel.addAttribute("totalPages", posts.getTotalPages());

        if (p != null) {
            String username = p.getName();
            homeModel.addAttribute("username", username);
            return "home";
        }

        return "home";
    }

    @GetMapping("/about")
    public String getAbout(Principal p, Model aboutModel) {
        if (p != null) {
            String username = p.getName();
            aboutModel.addAttribute("username", username);
            return "about";
        }
        return "about";
    }


    @PostMapping("/signup")
    public ModelAndView signupNormalUser(Principal p,
                                         @RequestParam String username,
                                         @RequestParam String password,
                                         @RequestParam String description,
                                         @RequestParam String email,
                                         @RequestParam String firstname,
                                         @RequestParam String lastname,
                                         @RequestParam String role,
                                         @RequestParam String country,
                                         Model model) {
        ModelAndView modelAndView = new ModelAndView();
        username = username.trim();
        if (isAlreadyLoggedIn(p)) {

            modelAndView.setViewName("redirect:/profile/" + p.getName());
        } else if (username.isEmpty() || username.isBlank()) {

            modelAndView.addObject("usernameError", "Username cannot be empty or contain only whitespace.");
            modelAndView.setViewName("redirect:/login");
        } else if (userSiteRepo.findByUsername(username) != null) {
            modelAndView.addObject("usernameError", "Username already exists. Please choose a different username.");
            modelAndView.setViewName("redirect:/login");

        } else if (userSiteRepo.findByEmail(email) != null) {
            modelAndView.addObject("usernameError", "Email already exists. Please choose a different email.");
            modelAndView.setViewName("redirect:/login");
        } else {
            if (isPasswordValidated(password)) {
                String encryptedPassword = passwordEncoder.encode(password);
                UserSiteEntity usersite = new UserSiteEntity();
                usersite.setUsername(username);
                usersite.setPassword(encryptedPassword);
                usersite.setDescription(description);
                usersite.setEmail(email);
                usersite.setFirstname(firstname);
                usersite.setLastname(lastname);
                usersite.setRoles(Role.valueOf(role));
                usersite.setCountry(country);
                userSiteRepo.save(usersite);
                System.out.println(usersite.getUsername());
                modelAndView.setViewName("redirect:/login");
            } else {
                modelAndView.addObject("usernameError", "Password should be 8 character including uppercase.");
                modelAndView.setViewName("redirect:/login");
            }
        }
        return modelAndView;
    }


    @PostMapping("/login")
    public RedirectView authWithHttpServletRequest(@RequestParam String username, @RequestParam String password) {
        try {
            request.login(username, password);
        } catch (ServletException e) {
            e.printStackTrace();
        }
        return new RedirectView("/signup");


    }

    private boolean isAlreadyLoggedIn(Principal p) {
        return p != null;
    }

    private boolean isPasswordValidated(String password) {
        if (password.length() < 8) {
            return false;
        } else {
            return password.matches("^(?=.*[a-z])(?=.*[A-Z]).+$");
        }
    }

    private final String[] allCountriesList = {
            "Afghanistan",
            "Åland Islands",
            "Albania",
            "Algeria",
            "American Samoa",
            "Andorra",
            "Angola",
            "Anguilla",
            "Antarctica",
            "Antigua and Barbuda",
            "Argentina",
            "Armenia",
            "Aruba",
            "Australia",
            "Austria",
            "Azerbaijan",
            "Bahamas",
            "Bahrain",
            "Bangladesh",
            "Barbados",
            "Belarus",
            "Belgium",
            "Belize",
            "Benin",
            "Bermuda",
            "Bhutan",
            "Bolivia (Plurinational State of)",
            "Bonaire and Sint Eustatius and Saba",
            "Bosnia and Herzegovina",
            "Botswana",
            "Bouvet Island",
            "Brazil",
            "British Indian Ocean Territory",
            "United States Minor Outlying Islands",
            "Virgin Islands (British)",
            "Virgin Islands (U.S.)",
            "Brunei Darussalam",
            "Bulgaria",
            "Burkina Faso",
            "Burundi",
            "Cambodia",
            "Cameroon",
            "Canada",
            "Cabo Verde",
            "Cayman Islands",
            "Central African Republic",
            "Chad",
            "Chile",
            "China",
            "Christmas Island",
            "Cocos (Keeling) Islands",
            "Colombia",
            "Comoros",
            "Congo",
            "Congo (Democratic Republic of the)",
            "Cook Islands",
            "Costa Rica",
            "Croatia",
            "Cuba",
            "Curaçao",
            "Cyprus",
            "Czech Republic",
            "Denmark",
            "Djibouti",
            "Dominica",
            "Dominican Republic",
            "Ecuador",
            "Egypt",
            "El Salvador",
            "Equatorial Guinea",
            "Eritrea",
            "Estonia",
            "Ethiopia",
            "Falkland Islands (Malvinas)",
            "Faroe Islands",
            "Fiji",
            "Finland",
            "France",
            "French Guiana",
            "French Polynesia",
            "French Southern Territories",
            "Gabon",
            "Gambia",
            "Georgia",
            "Germany",
            "Ghana",
            "Gibraltar",
            "Greece",
            "Greenland",
            "Grenada",
            "Guadeloupe",
            "Guam",
            "Guatemala",
            "Guernsey",
            "Guinea",
            "Guinea-Bissau",
            "Guyana",
            "Haiti",
            "Heard Island and McDonald Islands",
            "Holy See",
            "Honduras",
            "Hong Kong",
            "Hungary",
            "Iceland",
            "India",
            "Indonesia",
            "Côte d'Ivoire",
            "Iran (Islamic Republic of)",
            "Iraq",
            "Ireland",
            "Isle of Man",
            "Israel",
            "Italy",
            "Jamaica",
            "Japan",
            "Jersey",
            "Jordan",
            "Kazakhstan",
            "Kenya",
            "Kiribati",
            "Kuwait",
            "Kyrgyzstan",
            "Lao People's Democratic Republic",
            "Latvia",
            "Lebanon",
            "Lesotho",
            "Liberia",
            "Libya",
            "Liechtenstein",
            "Lithuania",
            "Luxembourg",
            "Macao",
            "Macedonia (the former Yugoslav Republic of)",
            "Madagascar",
            "Malawi",
            "Malaysia",
            "Maldives",
            "Mali",
            "Malta",
            "Marshall Islands",
            "Martinique",
            "Mauritania",
            "Mauritius",
            "Mayotte",
            "Mexico",
            "Micronesia (Federated States of)",
            "Moldova (Republic of)",
            "Monaco",
            "Mongolia",
            "Montenegro",
            "Montserrat",
            "Morocco",
            "Mozambique",
            "Myanmar",
            "Namibia",
            "Nauru",
            "Nepal",
            "Netherlands",
            "New Caledonia",
            "New Zealand",
            "Nicaragua",
            "Niger",
            "Nigeria",
            "Niue",
            "Norfolk Island",
            "Korea (Democratic People's Republic of)",
            "Northern Mariana Islands",
            "Norway",
            "Oman",
            "Pakistan",
            "Palau",
            "Palestine (State of)",
            "Panama",
            "Papua New Guinea",
            "Paraguay",
            "Peru",
            "Philippines",
            "Pitcairn",
            "Poland",
            "Portugal",
            "Puerto Rico",
            "Qatar",
            "Republic of Kosovo",
            "Réunion",
            "Romania",
            "Russian Federation",
            "Rwanda",
            "Saint Barthélemy",
            "Saint Helena and Ascension and Tristan da Cunha",
            "Saint Kitts and Nevis",
            "Saint Lucia",
            "Saint Martin (French part)",
            "Saint Pierre and Miquelon",
            "Saint Vincent and the Grenadines",
            "Samoa",
            "San Marino",
            "Sao Tome and Principe",
            "Saudi Arabia",
            "Senegal",
            "Serbia",
            "Seychelles",
            "Sierra Leone",
            "Singapore",
            "Sint Maarten (Dutch part)",
            "Slovakia",
            "Slovenia",
            "Solomon Islands",
            "Somalia",
            "South Africa",
            "South Georgia and the South Sandwich Islands",
            "Korea (Republic of)",
            "South Sudan",
            "Spain",
            "Sri Lanka",
            "Sudan",
            "Suriname",
            "Svalbard and Jan Mayen",
            "Swaziland",
            "Sweden",
            "Switzerland",
            "Syrian Arab Republic",
            "Taiwan",
            "Tajikistan",
            "Tanzania (United Republic of)",
            "Thailand",
            "Timor-Leste",
            "Togo",
            "Tokelau",
            "Tonga",
            "Trinidad and Tobago",
            "Tunisia",
            "Turkey",
            "Turkmenistan",
            "Turks and Caicos Islands",
            "Tuvalu",
            "Uganda",
            "Ukraine",
            "United Arab Emirates",
            "United Kingdom of Great Britain and Northern Ireland",
            "United States of America",
            "Uruguay",
            "Uzbekistan",
            "Vanuatu",
            "Venezuela (Bolivarian Republic of)",
            "Viet Nam",
            "Wallis and Futuna",
            "Western Sahara",
            "Yemen",
            "Zambia",
            "Zimbabwe"
    };
}

