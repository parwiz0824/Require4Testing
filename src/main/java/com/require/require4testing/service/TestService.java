package com.require.require4testing.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.require.require4testing.model.Test;
import com.require.require4testing.repository.TestRepository;

@Service
public class TestService {
    @Autowired
    private TestRepository testRepository;

    public List<Test> getAllTests() {
        return testRepository.findAll();
    }

    public Test saveTest(Test test) {
        return testRepository.save(test);
    }
    
}
