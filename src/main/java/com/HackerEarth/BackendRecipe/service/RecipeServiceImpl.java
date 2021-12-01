package com.HackerEarth.BackendRecipe.service;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.HackerEarth.BackendRecipe.Dao.RecipeRepository;
import com.HackerEarth.BackendRecipe.Model.RecipeTable;

@Service
public class RecipeServiceImpl implements RecipeService {

	@Autowired
	private RecipeRepository recipeRepository;
	
	@Override
	public List<RecipeTable> getAllRecipe() {
		return recipeRepository.findAll();
	}

	@Override
	public RecipeTable getRecipeService(int recipeId) {
		return recipeRepository.getOne(recipeId);
	}

	@Override
	public void AddRecipeService(RecipeTable recipe) {
		this.recipeRepository.save(recipe);
	}

//	Logic to fetch image from database
	
	@Override
	public byte[] getImageFromDatabaseImpl(int recipeId) {
		Optional<RecipeTable> option=recipeRepository.findById(recipeId);
		if(option.isPresent()) {
			RecipeTable rt=option.get();
			byte[] image=rt.getImages();
			return image;
		}
		return null;
	}

	

}
