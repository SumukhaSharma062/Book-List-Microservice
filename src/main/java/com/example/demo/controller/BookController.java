package com.example.demo.controller;

import com.example.demo.entity.Books;
import com.example.demo.repo.BooksRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class BookController {

    @Autowired
    private BooksRepo booksRepo;

    @GetMapping("/getAll")
    public ResponseEntity<List<Books>> getAllBooks(){
        try {
            List<Books> booksList = new ArrayList<>(booksRepo.findAll());

            if (booksList.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(booksList, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/getById/{id}")
    public ResponseEntity<Books> getById(@PathVariable long id){
    try{
        Optional<Books> bookdata = booksRepo.findById(id);
        if (bookdata.isPresent()){
            return new ResponseEntity<>(bookdata.get(), HttpStatus.OK);

        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }catch (Exception e){
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    }

    @PostMapping("/add")
    public ResponseEntity<Books> addBook(@RequestBody Books books){
        Books bookObj = booksRepo.save(books);
        return new ResponseEntity<>(bookObj, HttpStatus.OK);
    }

    @PostMapping("/upd/{id}")
    public ResponseEntity<Books> updateBookById(@PathVariable Long id, @RequestBody Books newBookData){
        Optional<Books> oldBookData = booksRepo.findById(id);

        if(oldBookData.isPresent()){
            Books updatedBookData = oldBookData.get();
            updatedBookData.setName(newBookData.getName());
            updatedBookData.setAuthor(newBookData.getAuthor());
            Books bookObj = booksRepo.save(updatedBookData);
            return new ResponseEntity<>(bookObj, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/del/{id}")
    public ResponseEntity<Object> deleteBookById(@PathVariable Long id){
        Optional<Books> bookdata = booksRepo.findById(id);

        if(bookdata.isPresent()){
            booksRepo.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(id, HttpStatus.NOT_FOUND);
    }


}
