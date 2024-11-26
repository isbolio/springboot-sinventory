package com.example.proyectoinventario2.services;

import com.example.proyectoinventario2.DAO.CategoryDAO;
import com.example.proyectoinventario2.DAO.ProductDAO;
import com.example.proyectoinventario2.model.Category;
import com.example.proyectoinventario2.model.Product;
import com.example.proyectoinventario2.response.CategoryResponseRest;
import com.example.proyectoinventario2.response.ProductResponse;
import com.example.proyectoinventario2.response.ProductResponseRest;
import com.example.proyectoinventario2.util.util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Base64;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service

public class ProductServicesIMPL implements ProductServices{

    private CategoryDAO categoryDao;

    public ProductServicesIMPL(CategoryDAO categoryDao) {
        super();
        this.categoryDao = categoryDao;
    }

    @Autowired
    public ProductDAO productDAO;


// ...

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<ProductResponseRest> AllProducts() {
        ProductResponseRest response = new ProductResponseRest();
        List<Product> lista = (List<Product>) productDAO.findAll();
        List<Product> aux = new ArrayList<>();

        if(lista.size() > 0){
            lista.stream().forEach( (p) ->{
                byte[] picture = util.decompressZLib(p.getPicture());
                p.setPicture(picture);
                aux.add(p);
            });
            response.getProductResponse().setProductList(aux);
            response.setMetadata("OK","00","Productos encontrados");
        } else {
            response.setMetadata("NO OK","-1","Productos no encontrados");
            return new ResponseEntity<ProductResponseRest>(response, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<ProductResponseRest>(response, HttpStatus.OK);
    }


    @Override
    public ResponseEntity<ProductResponseRest> SaveProduct(Product product,Long categoryId) {
        ProductResponseRest response = new ProductResponseRest();
        List<Product> lista = new ArrayList<>();

        try {
            Optional<Category> category = categoryDao.findById(categoryId);
            if(category.isPresent()){
                product.setCategory(category.get());

            }else{
                response.setMetadata("No ok","01","Mala");
                return new ResponseEntity<ProductResponseRest>(response,HttpStatus.NOT_FOUND);
            }
            //Creacion producto
            Product productSaved = productDAO.save(product);

            if(productSaved != null){
                lista.add(productSaved);
                response.getProductResponse().setProductList(lista);
                response.setMetadata("OK","00","Producto guardado");
            }else {
                response.setMetadata("No ok","01","Mala");
                return new ResponseEntity<ProductResponseRest>(response,HttpStatus.BAD_REQUEST);
            }
        }catch(Exception e)
        {
            e.getStackTrace();
            response.setMetadata("No ok","-1","Error al guardar");
            return new ResponseEntity<ProductResponseRest>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<ProductResponseRest>(response,HttpStatus.OK);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<ProductResponseRest> GetById(Long id) {
        ProductResponseRest response = new ProductResponseRest();
        Optional<Product> optionalProduct = productDAO.findById(id);
        List<Product> lista = new ArrayList<>();

        if(optionalProduct.isPresent()){
            byte [] imageDescompressed = util.decompressZLib(optionalProduct.get().getPicture());
            optionalProduct.get().setPicture(imageDescompressed);
            lista.add(optionalProduct.get());
            response.getProductResponse().setProductList(lista);
            response.setMetadata("OK","00","Bueno");
        }else{
            response.setMetadata("No OK","01","Malo");
            return new ResponseEntity<ProductResponseRest>(response,HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<ProductResponseRest>(response,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ProductResponseRest> GetByName(String name) {
        ProductResponseRest response = new ProductResponseRest();
        List<Product> aux = new ArrayList<>();
        List<Product> lista = productDAO.findByNameContainingIgnoreCase(name);
        try{
            if(lista.size() > 0){
                lista.stream().forEach( (p) ->{
                    byte[] image = util.decompressZLib(p.getPicture());
                    p.setPicture(image);
                    aux.add(p);
                });
                response.getProductResponse().setProductList(aux);
                response.setMetadata("Bueno","00","Productos encontrados");
            }
        }catch(Exception e){
            e.getStackTrace();
            response.setMetadata("Malo","-1","Productos no encontrados");
            return new ResponseEntity<ProductResponseRest>(response,HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<ProductResponseRest>(response,HttpStatus.OK);
    }


    @Override
    @Transactional
    public ResponseEntity<ProductResponseRest> DeleteById(Long id) {
        ProductResponseRest response = new ProductResponseRest();
        List<Product> lista = new ArrayList<>();
        Optional<Product> productDelete = productDAO.findById(id);

        if(productDelete.isPresent()){
            productDAO.deleteById(id);
            lista.add(productDelete.get());
            response.getProductResponse().setProductList(lista);
            response.setMetadata("Ok","00","Bueno");
        }else{
            response.setMetadata("No Ok","-1","Malo");
            return new ResponseEntity<ProductResponseRest>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<ProductResponseRest>(response,HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<ProductResponseRest> UpdateProduct(Product product, Long categoryId,Long id) {
        ProductResponseRest response = new ProductResponseRest();
        Optional<Category> category= categoryDao.findById(categoryId);
        List<Product> lista = new ArrayList<>();

        if(category.isPresent()){
            product.setCategory(category.get());
        }else{
            response.setMetadata("No OK","-1","Producto no Actualizado");
            return new ResponseEntity<ProductResponseRest>(response,HttpStatus.NO_CONTENT);
        }

        Optional<Product> productSearch = productDAO.findById(id);
        if(productSearch.isPresent()){

            productSearch.get().setName(product.getName());
            productSearch.get().setPrice(product.getPrice());
            productSearch.get().setAccount(product.getAccount());
            productSearch.get().setCategory(product.getCategory());
            productSearch.get().setPicture(product.getPicture());

            Product productSave = productDAO.save(productSearch.get());

            if(productSave != null){
                lista.add(productSave);
                response.getProductResponse().setProductList(lista);
                response.setMetadata("Ok","00","Producto Actualizado");
            }else{
                response.setMetadata("No OK","-1","Producto No Actualizado");
                return new ResponseEntity<ProductResponseRest>(response,HttpStatus.NOT_FOUND);
            }
        }else{
            response.setMetadata("No OK","-1","Producto No Actualizado");
            return new ResponseEntity<ProductResponseRest>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<ProductResponseRest>(response,HttpStatus.OK);

    }


}
