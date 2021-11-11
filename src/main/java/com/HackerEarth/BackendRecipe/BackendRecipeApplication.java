package com.HackerEarth.BackendRecipe;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.HackerEarth.BackendRecipe.Dao.RecipeRepository;
import com.HackerEarth.BackendRecipe.Model.RecipeTable;

@SpringBootApplication
public class BackendRecipeApplication implements CommandLineRunner {

	@Autowired
	private RecipeRepository recipeRepository;
	@Autowired
	private RestTemplate restTemplate;
	
	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
	
	public static void main(String[] args) {
		SpringApplication.run(BackendRecipeApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		String url="https://s3-ap-southeast-1.amazonaws.com/he-public-data/reciped9d7b8c.json";
		RecipeTable[] objects=restTemplate.getForObject(url,RecipeTable[].class);
		List<RecipeTable> res=Arrays.asList(objects);
		for(RecipeTable r:res) {
			RecipeTable recipeTable=new RecipeTable();
			recipeTable.setId(r.getId());
			recipeTable.setName(r.getName());
			recipeTable.setCategory(r.getCategory());
			recipeTable.setDescription(r.getDescription());
			recipeTable.setLabel(r.getLabel());
			recipeTable.setImage(r.getImage());
			recipeTable.setPrice(r.getPrice());
			
//			Convert to byte Array and stores in the Database as a array
			
//			String Url=r.getImage();
//			byte[] img=Url.getBytes();
//			recipeTable.setImages(img);
			recipeRepository.save(recipeTable);
		}
	}

}
