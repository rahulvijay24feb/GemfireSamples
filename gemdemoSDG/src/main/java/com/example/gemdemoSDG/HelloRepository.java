package com.example.gemdemoSDG;

import org.springframework.data.gemfire.repository.GemfireRepository;

public interface HelloRepository extends GemfireRepository<Greeting, String> {
}
