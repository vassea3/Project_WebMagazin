package com.mycompany.controllers;

import com.mycompany.model.Images;
import com.mycompany.model.Products;
import com.mycompany.model.Users;
import com.mycompany.service.intf.ContactPageServiceIntf;
import com.mycompany.service.intf.HomeSliderServiceIntf;
import com.mycompany.service.intf.ImagesServiceIntf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.mycompany.service.intf.ProductsServiceIntf;
import com.mycompany.service.intf.UsersServiceIntf;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.multipart.MultipartFile;

@Controller
@SessionAttributes({"category", "contact", "company", "allProduct"})
public class ProductsController {

    @Autowired(required = true)
    @Qualifier(value = "productsService")
    ProductsServiceIntf productsServiceIntf;

    @Autowired(required = true)
    @Qualifier(value = "imageService")
    private ImagesServiceIntf imageServiceIntf;

    @Autowired(required = true)
    @Qualifier(value = "contactService")
    ContactPageServiceIntf contPageServiceIntf;

    @Autowired(required = true)
    @Qualifier(value = "homeSlideService")
    HomeSliderServiceIntf homeSliderService;

    @Autowired(required = true)
    @Qualifier(value = "usersService")
    UsersServiceIntf usersServiceIntf;

    static List<Images> listImages = new ArrayList<Images>();
    static Products prod = new Products();
    static Products prod1 = new Products();
    static List<Images> deleteImgList = new ArrayList<Images>();
    static List<Products> productList = new ArrayList<Products>();

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String showClientHome(Model model) {
        List<String> categoryList = new ArrayList<String>();
        Map<String, Map> mapMenu = new HashMap<String, Map>();
        categoryList.addAll(productsServiceIntf.findByCategory());
        Set<String> setCategory = new HashSet<String>(categoryList);
        List<String> companyList = new ArrayList<String>();
        Set<String> setCompany = new HashSet<String>(productsServiceIntf.findAllCompany());
        for (String category : setCategory) {
            List<String> subcategoryList = new ArrayList<String>();
            subcategoryList.addAll(productsServiceIntf.findBySubcategory(category));
            Set<String> setSubcategory = new HashSet<String>(subcategoryList);
            Map<String, Set> map = new HashMap<String, Set>();
            for (String subcategory : setSubcategory) {
                List<String> tipul = new ArrayList<String>();
                tipul.addAll(productsServiceIntf.findByTip(subcategory));
                Set<String> setTipul = new HashSet<String>(tipul);

                map.put(subcategory, setTipul);

            }

            mapMenu.put(category, map);

        }
        for (String string : setCompany) {
            companyList.add(string);
        }

        model.addAttribute("homeSlide1", this.homeSliderService.findById(1));
        model.addAttribute("homeSlide2", this.homeSliderService.findById(2));
        model.addAttribute("homeSlide3", this.homeSliderService.findById(3));
        model.addAttribute("category", mapMenu);
        model.addAttribute("company", companyList);
        model.addAttribute("contact", this.contPageServiceIntf.findById(1));
        //subcategoryList.addAll(productsServiceIntf.findBySubcategory());
        return "home";
    }

    @RequestMapping(value = "/cmsHome", method = RequestMethod.GET)
    public String showAdminHome(HttpServletRequest request, Model model) {
        String page = null;
        HttpSession session = request.getSession();
        if (session.getAttribute("user") == null) {
            page = "redirect:/login";
        } else {
            page = "cmsHome";
        }
         model.addAttribute("contact", this.contPageServiceIntf.findById(1));
        return page;
    }

    @RequestMapping("/cmsLogout")
    public String logout(HttpServletRequest request) {

        HttpSession session = request.getSession();
        // System.out.println(session.getAttributeNames());
        session.invalidate();
        return "redirect:/login";
    }

    @RequestMapping("/cart")
    public String showCart(HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {

        Cookie[] cookie = request.getCookies();
        Map<String, Products> prodMap = new HashMap<String, Products>();
        //List<Products> prodList = new ArrayList<Products>();
        String image = "";
        for (Cookie obj : cookie) {
            Products product = new Products();
            product = this.productsServiceIntf.findProductByModel(obj.getName());
            if (product != null) {
               
                image=product.getImages().get(0).getImgName();
                prodMap.put(image, product);
            }
        }
        model.addAttribute("prodlist", prodMap);
        return "cart";
    }

    @RequestMapping(value = "/addToCart/{modelul}", method = RequestMethod.GET)
    public String addToCart(@PathVariable("modelul") String modelul, HttpServletResponse response) {
        Cookie cooki = new Cookie(modelul, modelul);
        cooki.setPath("/");
        response.addCookie(cooki);
        return "redirect:/home";
    }

    @RequestMapping(value = "/deleteProd/{modelul}", method = RequestMethod.GET)
    public String deleteFromCart(@PathVariable("modelul") String modelul, HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (int i = 0; i < cookies.length; i++) {
                Cookie cooki = new Cookie(modelul, modelul);
                cooki = cookies[i];
                if (cooki.getName().equals(modelul)) {
                    cooki.setPath("/");
                    cooki.setMaxAge(0);
                    response.addCookie(cooki);
                }
            }
        }
        return "redirect:/cart";
    }

    @RequestMapping("/showDetails")
    public String showDetails(Model model
    ) {
        List<String> imgList = new ArrayList<String>();
        for (Images images : prod1.getImages()) {
            imgList.add(images.getImgName());
        }
        model.addAttribute("product", prod1);
        model.addAttribute("imgList", imgList);
        return "productDetails";
    }

    @RequestMapping(value = "/products/{model}/productDetails", method = RequestMethod.GET)
    public String showProductDetails(@PathVariable("model") String modelul, Model model
    ) {
        prod1 = null;
        Products product = this.productsServiceIntf.findProductByModel(modelul);

        prod1 = product;

        return "redirect:/showDetails";
    }

    @RequestMapping(value = "/cmsProductManagement", method = RequestMethod.GET)
    public String showProductManagement(Model model, HttpServletRequest request
    ) {
        String page = "";
        HttpSession session = request.getSession();
        if (session.getAttribute("user") == null) {
            page = "redirect:/login";
        } else {
            List<String> listImg = new ArrayList<String>();
            if (prod.getProductId() != 0) {

                for (Images image : listImages) {
                    listImg.add(image.getImgName());
                    System.out.println(image.getImgName());
                }
                model.addAttribute("imgList", listImg);
                model.addAttribute("product", prod);
                model.addAttribute("id", prod.getProductId());
                prod = null;
            } else {
                model.addAttribute("id", 0);
            }
            page = "cmsProductManagement";
        }
        return page;
    }

    @RequestMapping(value = "/cmsEditProduct/{modelul}", method = RequestMethod.GET)
    public String cmsEditProduct(@PathVariable("modelul") String modelul, Model model
    ) {
        Products product = this.productsServiceIntf.findProductByModel(modelul);

        listImages.clear();
        listImages.addAll(product.getImages());
        model.addAttribute("product", product);

        return "redirect:/cmsProductManagement";
    }

    @RequestMapping(value = "/{modelul}/{img}", method = RequestMethod.GET)
    public String cmsDeleteImage(@PathVariable("modelul") String modelul,
            @PathVariable("img") String imgName, Model model
    ) {
        Products product = this.productsServiceIntf.findProductByModel(modelul);
        List<Images> iList = new ArrayList<Images>();
        iList.addAll(listImages);
        listImages.clear();
        System.out.println(imgName);
        for (Images image : iList) {
            if (!image.getImgName().equals(imgName + image.getImgName().substring(image.getImgName().indexOf("."), image.getImgName().length()))) {
                System.out.println(image.getImgName());
                System.out.println(imgName);
                listImages.add(image);
                //this.imageServiceIntf.delete(image);
            } else {

                deleteImgList.add(image);
            }
        }

        List<String> imgList = new ArrayList<String>();
        for (Images images : listImages) {
            imgList.add(images.getImgName());
        }
        model.addAttribute("imgList", imgList);
        model.addAttribute("product", product);
        prod = product;
        iList.clear();
        return "redirect:/cmsProductManagement";
    }

    @RequestMapping(value = "/cmsDeleteProduct/{modelul}", method = RequestMethod.GET)
    public String cmsDeleteProduct(@PathVariable("modelul") String modelul, Model model
    ) {
        Products product = this.productsServiceIntf.findProductByModel(modelul);
        this.productsServiceIntf.delete(product);
        return "redirect:/cmsHome";
    }

    @RequestMapping("/showProducts")
    public String showProducts1(Model model
    ) {
        Map<Products, List> prodMap = new HashMap<Products, List>();
        for (Products products : productList) {
            List<String> imList = new ArrayList<String>();
            for (Images image : products.getImages()) {
                imList.add(image.getImgName());
            }
            prodMap.put(products, imList);
        }
        model.addAttribute("titleProducts", "All products");
        model.addAttribute("prodList", prodMap);

        return "products";
    }

    @RequestMapping("/products/{categoria}")
    public String showProducts(@PathVariable("categoria") String categoria, Model model
    ) {
        productList.clear();
        List<Products> prodList = new ArrayList<Products>();
        prodList.addAll(this.productsServiceIntf.findProductsByCategopria(categoria));
        model.addAttribute("allProduct", "in " + categoria);
        if (prodList.isEmpty()) {
            prodList.addAll(this.productsServiceIntf.findProductsBySubcategopria(categoria));
        }
        if (prodList.isEmpty()) {
            prodList.addAll(this.productsServiceIntf.findProductsByTip(categoria));

        }
        if (prodList.isEmpty()) {
            prodList.addAll(this.productsServiceIntf.findProductsByCompany(categoria));
        }
        productList.addAll(prodList);

        return "redirect:/showProducts";
    }

    @RequestMapping("searchEdit")
    public String editSearchProduct(@RequestParam String modelul, HttpServletRequest request,
            Model model
    ) {
        String page = "";
        HttpSession session = request.getSession();
        System.out.println(session.getAttribute("user"));

        if (session.getAttribute("user") == null) {
            page = "redirect:/login";
        } else {
            Products product = new Products();
            product = this.productsServiceIntf.findProductByModel(modelul);
            if (product == null) {
                product = this.productsServiceIntf.findProductByCod(modelul);
            }
            listImages.clear();
            listImages.addAll(product.getImages());
            List<String> imgList = new ArrayList<String>();
            for (Images images : listImages) {
                imgList.add(images.getImgName());

            }
            model.addAttribute("product", product);
            model.addAttribute("imgList", imgList);
            System.out.println(product);
            prod = product;
            deleteImgList.clear();
            page = "cmsOneProduct";
        }
        return page;

    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public String searchProduct(@RequestParam("model") String modelul, Model model
    ) {
        Products product = new Products();
        product = this.productsServiceIntf.findProductByModel(modelul);
        if (product != null) {
            List<String> imgList = new ArrayList<String>();
            for (Images image : product.getImages()) {
                imgList.add(image.getImgName());
            }
            model.addAttribute("product", product);
            model.addAttribute("imgList", imgList);
        } else if (product == null) {
            product = this.productsServiceIntf.findProductByCod(modelul);
            if (product != null) {
                List<String> imgList = new ArrayList<String>();
                for (Images image : product.getImages()) {
                    imgList.add(image.getImgName());
                }
                model.addAttribute("product", product);
                model.addAttribute("imgList", imgList);
            } else {
                model.addAttribute("error", "We do not have product what you search");
            }
        }
        return "productDetails";
    }

    @RequestMapping(value = "/saveProduct", method = RequestMethod.POST)
    public String saveProduct(@RequestParam("categoria") String categoria,
            @RequestParam("subcategoria") String subcategoria,
            @RequestParam("tipul") String tipul,
            @RequestParam("modelul") String modelul,
            @RequestParam("pret") String pret,
            @RequestParam("cod") String cod,
            @RequestParam("descrierea") String descrierea,
            @RequestParam("compania") String compania,
            @RequestParam("productId") int productId,
            @RequestParam("imgFiles") MultipartFile[] imgFiles) throws IOException {
        List<Images> imageList = new ArrayList<Images>();

        Products product = new Products();
        product.setProductId(productId);
        product.setCategoria(categoria);
        product.setCod(cod);
        product.setCompania(compania);
        product.setModelul(modelul);
        product.setPret(pret);
        product.setSubcategoria(subcategoria);
        product.setTipul(tipul);
        product.setDescrierea(descrierea);

        for (MultipartFile file : imgFiles) {
            Images image = new Images();
            image.setImgName(file.getOriginalFilename());
            if (!file.getOriginalFilename().isEmpty()) {
                image.setImgFile(readByte(file));
                imageList.add(image);
            }
        }
        imageList.addAll(listImages);
        product.setImages(imageList);
//        for (Images img : listImages) {
//            System.out.println(img.getImgName());
//        }
        if (product.getProductId() == 0) {
            this.productsServiceIntf.save(product);
        } else {
            this.productsServiceIntf.update(product);//update
            if (!deleteImgList.isEmpty()) {

                for (Images images : deleteImgList) {
                    this.imageServiceIntf.delete(images);
                }
            }
        }
        deleteImgList.clear();
        return "redirect:/cmsHome";
    }

    public byte[] readByte(MultipartFile file) throws FileNotFoundException, IOException {
        FileInputStream fis = null;
        HttpServletRequest request = null;
        String uploadsDir = "/resources/productsImages/";
        String imgPath = ContextLoader.getCurrentWebApplicationContext().getServletContext().getRealPath(uploadsDir);
        File file1 = new File(imgPath + file.getOriginalFilename());
        // FileOutputStream fos = new FileOutputStream(file1);
        byte[] bytes = null;
        try {
            fis = (FileInputStream) file.getInputStream();
            bytes = new byte[fis.available()];
            fis.read(bytes);
            //  fos.write(bytes);
            fis.close();
            //  fos.close();
        } catch (Exception e) {

        }
        return bytes;
    }

    @RequestMapping(value = "/login.do", method = RequestMethod.POST)
    public String login(@ModelAttribute Users users, Model model, HttpServletRequest request) {
        Users user = new Users();
        String username = users.getUsername();
        user = this.usersServiceIntf.findByUsername(username);
        String page = "redirect:/home";
        if (user != null) {
            if (user.getPassword().equals(users.getPassword())) {
                page = "cmsHome";
                HttpSession session = request.getSession();
                session.setAttribute("user", user);

            } else {
                System.out.println("password or username is incorect");
            }
        } else {
            System.out.println("password or username is incorect");
        }
        return page;

    }
}
