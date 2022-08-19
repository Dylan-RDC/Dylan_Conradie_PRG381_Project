// package com.studentportal.webapp.security;

// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

// import com.studentportal.webapp.models.Iuser;
// import com.studentportal.webapp.models.admin;

// public class passwordGen {
//     public static void main(String[] args) {
//         BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);
//         String rawPassword = "okay";
//         String encoded = encoder.encode(rawPassword);
//         if (encoder.matches("okay", "$2a$10$zneE1Q2qRqHACbSV6ct8NOxEzisNaynium6SCuM9HPj1Pl4ohh5EK")) {
//             System.out.println("MATCHES");
//         }
//         Iuser ad = new admin();



//         System.out.println(encoded);
//     }
// }
