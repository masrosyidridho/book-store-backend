package com.rosyid.book.store.transaction.controller;


import com.rosyid.book.store.transaction.payload.request.TransactionRequest;
import com.rosyid.book.store.transaction.payload.request.TransactionRequestUpdate;
import com.rosyid.book.store.transaction.payload.response.TransactionResponse;
import com.rosyid.book.store.transaction.service.TransactionService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Api
@RestController
@RequestMapping("/api/v1/user/transactions")
//@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
public class TransactionRestController
{
    @Autowired
    private TransactionService transactionService;

    @PostMapping("/checkout")
    public TransactionResponse checkout(@RequestBody @Valid TransactionRequest request,
                                        BindingResult result,
                                        HttpServletResponse response) throws IOException
    {
        TransactionResponse transactionModel = new TransactionResponse();
        if (result.hasErrors()) {
            response.sendError(HttpStatus.BAD_REQUEST.value(), result.getAllErrors().toString());
            return transactionModel;
        } else
            return transactionService.save(request);
    }

    // FIXME: separate into confirm payment and settlement
    @PostMapping("/payment")
    public TransactionResponse payment(@RequestBody @Valid TransactionRequestUpdate request,
                                       BindingResult result,
                                       HttpServletResponse response) throws IOException
    {
        TransactionResponse transactionModel = new TransactionResponse();
        if (result.hasErrors()) {
            response.sendError(HttpStatus.BAD_REQUEST.value(), result.getAllErrors().toString());
            return transactionModel;
        } else
            return transactionService.update(request);
    }

    @GetMapping("/findAll")
    public List<TransactionResponse> findAll()
    {
        return transactionService.findAll();
    }

    @GetMapping("/findById/{id}")
    public TransactionResponse findById(@PathVariable("id") final Long id)
    {
        return transactionService.findById(id);
    }

    @GetMapping("/findByUserId/{userId}")
    public List<TransactionResponse> findByUserId(@PathVariable("userId") final Long userId)
    {
        return transactionService.findByUserId(userId);
    }
}
