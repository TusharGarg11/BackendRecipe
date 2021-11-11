package com.HackerEarth.BackendRecipe.Dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.HackerEarth.BackendRecipe.Model.RecipeTable;

public interface RecipeRepository extends JpaRepository<RecipeTable, Integer>{

}
