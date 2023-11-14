package com.example.ProjectAlias03.Controller;

import com.example.ProjectAlias03.Entity.CategoryEntity;
import com.example.ProjectAlias03.Entity.ProductEntity;
import com.example.ProjectAlias03.Repository.CategoryRepository;

import com.example.ProjectAlias03.Repository.ImageRepository;
import com.example.ProjectAlias03.Repository.ProductRepository;
import com.example.ProjectAlias03.Service.Imp.ProductServiceImp;
import com.example.ProjectAlias03.Service.JsonService;
import com.example.ProjectAlias03.Service.ProductService;
import com.example.ProjectAlias03.payload.BaseResponse;
import com.example.ProjectAlias03.payload.request.ProductRequest;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@CrossOrigin
@RestController
@RequestMapping("/product")
public class ProductController {
    private static final String UPLOAD_DIRECTORY = System.getProperty("products.dir") + "/src/main/front-end\\allaia/img";

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductServiceImp productServiceImp;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired

    private ImageRepository imageRepository;

    @Autowired
    private JsonService jsonService;

    @Value("${root.file.path}")
    private String rootPath;

    Logger logger = LoggerFactory.getLogger(ProductController.class);

//    private Gson gson = new Gson();
//
//    ProductRequest[] productArray = new Gson().fromJson(jsonArray, ProductRequest[].class);

    @GetMapping("/all")
    public ResponseEntity<?> getAllProducts(){
        List<ProductEntity> products = productServiceImp.getAllProduct();
        if(products != null && !products.isEmpty()){
            return new ResponseEntity<>(products,HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/get/{id}")
    public ResponseEntity<?> getProductById(@PathVariable int id){
        ProductEntity product = productServiceImp.getProductById(id);
        if(product != null){
            return new ResponseEntity<>(product,HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
@GetMapping("/getDtail/{id}")
    public ResponseEntity<?> getDetailProduct(@PathVariable("id") int id){
    BaseResponse baseResponse = new BaseResponse();
    return  new ResponseEntity<>(baseResponse, HttpStatus.OK);
}
    @PostMapping("/edit/{id}")
    public ResponseEntity<?> editProduct(@PathVariable  int id,
                                         @ModelAttribute("product") ProductRequest updatedProduct,
                                         @RequestParam("imageFile") MultipartFile imageProduct,
                                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body("Errors in the submitted data.");
        }

        try {
            ProductEntity product =productServiceImp.getProductById(id);
            if (product != null) {
                // Cập nhật thông tin sản phẩm từ updatedProduct vào sản phẩm tương ứng
                product.setName(updatedProduct.getName());
                product.setPrice(updatedProduct.getPrice());
                product.setQuantity(updatedProduct.getQuantity());
                product.setDiscount(updatedProduct.getDiscount());
                product.setDescription(updatedProduct.getDescription());

                // Xử lý và cập nhật ảnh sản phẩm (nếu có)
                if (imageProduct != null && imageProduct.getSize() > 0) {
                    String fileName = imageProduct.getOriginalFilename();
                    String rootFolder = rootPath;
                    Path pathRoot = Paths.get(rootFolder);
                    if (!Files.exists(pathRoot)) {
                        Files.createDirectory(pathRoot);
                    }
                    Files.copy(imageProduct.getInputStream(), pathRoot.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
//                    updatedProduct.setImageId(fileName.toString());
//                    product.setImage(fileName); // Cập nhật tên file ảnh mới
                    List<String> imageIdList = updatedProduct.getImageId();
                    imageIdList.add(fileName.toString());
                    updatedProduct.setImageId(imageIdList);
                }

                // Gọi phương thức trong service hoặc repository để cập nhật thông tin sản phẩm
                productServiceImp.addProduct(updatedProduct);

                return ResponseEntity.ok("Product Update Successful");
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update product");
        }
    }
    @PostMapping("/save")
    public ResponseEntity<?>  saveProduct(@RequestBody ProductRequest productRequest){
        boolean result = productServiceImp.saveProduct(productRequest);
        if(result){
            return  new ResponseEntity<>(HttpStatus.CREATED);
        }else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        //       try{
//           productRepository.save(product);
//           redirectAttributes.addFlashAttribute("message","The Product has been saved successfully!");
//       }catch(Exception e){
//           redirectAttributes.addAttribute("message", e.getMessage());
//       }
//       return "redirect:/products";
//       iProductService.saveProduct(product);
//       return new ResponseEntity<>("Product save Successfully", HttpStatus.OK);
    }
    @DeleteMapping("delete/{id}")
    public String deleteProductById(@PathVariable int id, Model model, RedirectAttributes redirectAttributes) {
        try{
            productRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("message", "The product with id=" + id + "has been delete successfully!");

        }catch (Exception e){
            redirectAttributes.addFlashAttribute("mesage", e.getMessage());
        }
        return "redirect:/products";
    }

    @GetMapping("/clear-cache")
    public ResponseEntity<?> clearCache(){
        productServiceImp.clearCache();
        return new ResponseEntity<>("",HttpStatus.OK);
    }
    @GetMapping("/category/{id}")
    public ResponseEntity<?> getProductByCategory(HttpServletRequest request, @PathVariable int id){

        String hostName = request.getHeader("host");

        BaseResponse response = new BaseResponse();
        response.setData(productServiceImp.getProductByCategoryId(hostName,id));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/file/{filename}")
    public ResponseEntity<?> downloadFileProduct(@PathVariable String filename) throws FileNotFoundException {
        try{

            Path path = Paths.get(rootPath);


            Path pathFile = path.resolve(filename);
            Resource resource = new UrlResource(pathFile.toUri());
            if(resource.exists() || resource.isReadable()){
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                        .body(resource);
            }else {
                throw new FileNotFoundException("Khong tim thay file");
            }
        } catch (Exception e) {
            //loi
            throw new FileNotFoundException("khong tim thay file");
        }
    }
    @PostMapping("/add")
    public ResponseEntity<?> addProduct(@Valid  @PathVariable ProductRequest productRequest) {
        String fileName = productRequest.getFile().getOriginalFilename();
        try {
            // Tạo thư mục lưu trữ nếu chưa tồn tại

            String rootFolder = rootPath;
            Path pathRoot = Paths.get(rootFolder);
            if (!Files.exists(pathRoot)) {
                Files.createDirectory(pathRoot);

            }

            Files.copy(productRequest.getFile().getInputStream(), pathRoot.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
            // Tạo đối tượng Product từ dữ liệu trong ProductRequest
            ProductRequest product = new ProductRequest();
            product.setName(product.getName());
            product.setPrice(product.getPrice());

            productServiceImp.addProduct(product);
            return new ResponseEntity<>("Product added successfully", HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>("Failed to add product", HttpStatus.BAD_REQUEST);

        }
    }

    @GetMapping("/search")
    public String searchProduct (@RequestParam String keyword, Model model){
        try{
            List<ProductEntity> productEntities = new ArrayList<>();
            if(keyword != null){
                productRepository.findAll().forEach(productEntities :: add);
            }else {
                productRepository.findByNameContaining(keyword).forEach(productEntities :: add);
                model.addAttribute("keyword",keyword);

            }
            model.addAttribute("products",productEntities);
        }catch (Exception e){
            model.addAttribute("message", e.getMessage());
        }
        return "products";
    }

    @PostMapping("/create")
    public ResponseEntity<?> createProduct(@RequestBody ProductRequest request,
                                           @RequestParam("imageFile") MultipartFile imageProduct,
                                           @RequestParam("categoryId") int categoryId,
                                           BindingResult result, Model model) throws IOException {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON); // Hoặc bất kỳ kiểu nào bạn muốn đặt


//        List<String> imageIdList = JsonService.fromJsonArray(request.toString(), String.class);

        // Kiểm tra lỗi từ request
        if (result.hasErrors()) {
            // Trả về các thông báo lỗi chi tiết
            return new ResponseEntity<>("Invalid data submitted", HttpStatus.BAD_REQUEST);
        }

        // Lấy thông tin về category
        Optional<CategoryEntity> category = categoryRepository.findById(categoryId);
        if (category.isPresent()) {
            CategoryEntity categoryEntity = category.get();
            request.setCategoryId(categoryEntity.getId_category());

            // Xử lý file và lưu tên file vào danh sách imageId
            if (imageProduct != null && imageProduct.getSize() > 0) {
                StringBuilder fileName = new StringBuilder();
                Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, imageProduct.getOriginalFilename());
                fileName.append(imageProduct.getOriginalFilename());
                Files.write(fileNameAndPath, imageProduct.getBytes());

                List<String> imageIdList = request.getImageId();
                if (imageIdList == null) {
                    imageIdList = new ArrayList<>();
                }

                imageIdList.add(fileName.toString());
                request.setImageId(imageIdList);
            } else {
                return new ResponseEntity<>("File not found or invalid", HttpStatus.BAD_REQUEST);
            }

            // Tạo một đối tượng Product từ dữ liệu trong ProductRequest
            ProductRequest productRequest = new ProductRequest();
            productRequest.setName(request.getName());
            productRequest.setPrice(request.getPrice());
            productRequest.setDescription(request.getDescription());


            // Lưu sản phẩm vào cơ sở dữ liệu
            boolean savedProduct = productServiceImp.saveProduct(request);

            // Trả về thông tin sản phẩm sau khi lưu vào cơ sở dữ liệu
            return new ResponseEntity<>(savedProduct, HttpStatus.OK);

        } else {
            return new ResponseEntity<>("Category not found", HttpStatus.BAD_REQUEST);
        }
    }
}
