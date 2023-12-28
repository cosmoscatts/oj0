package com.cc.oj0backend.github.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class GithubUserPlan {

    private String name;

    private int space;

    private int collaborators;

    private int private_repos;
}
