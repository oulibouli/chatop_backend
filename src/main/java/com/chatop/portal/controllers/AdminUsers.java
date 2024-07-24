// package com.chatop.portal.controllers;

// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.RestController;



// @RestController
// public class AdminUsers {

//     // @Autowired
//     // private ProductRepo productRepo;

//     // @GetMapping("/public/product")
//     // public ResponseEntity<Object> getAllProducts() {
//     //     return ResponseEntity.ok(productRepo.findAll());
//     // }

//     // @PostMapping("/admin/saveproduct")
//     // public ResponseEntity<Object> signUp(@RequestBody ReqRes productRequest) {
//     //     Product productToSave = new Product();
//     //     productToSave.setName(productRequest.getName());
//     //     return ResponseEntity.ok(productRepo.save(productToSave));
//     // }

//     @GetMapping("/user/alone")
//     public ResponseEntity<Object> userAlone() {
//         return ResponseEntity.ok("Users alone can access this API only");
//     }
    
//     @GetMapping("/adminuser/both")
//     public ResponseEntity<Object> bothAdminAndUsersApi() {
//         return ResponseEntity.ok("Both admin and users can access this API");
//     }
// }
