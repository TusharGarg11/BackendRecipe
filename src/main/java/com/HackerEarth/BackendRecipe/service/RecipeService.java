package com.HackerEarth.BackendRecipe.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.HackerEarth.BackendRecipe.Model.RecipeTable;

public interface RecipeService {
	public List<RecipeTable> getAllRecipe();
	public RecipeTable getRecipeService(int recipeId);
	public void AddRecipeService(RecipeTable recipe);
	public byte[] getImageFromDatabaseImpl(int recipeId);
	
}
