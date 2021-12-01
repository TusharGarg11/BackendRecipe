package com.HackerEarth.BackendRecipe;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
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

	
//	Convert a Image into Byte and Store it into the Byte Array
	
	public byte[] convertImageByte(URL url) throws IOException{
		ByteArrayOutputStream baos=new ByteArrayOutputStream();
		InputStream is=new BufferedInputStream(url.openStream());
		byte[] byteChunk = new byte[4096];
		int n;
		while((n=is.read(byteChunk))>0) {
			baos.write(byteChunk, 0, n);
		}
		return baos.toByteArray();
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
			URL url1 = new URL(r.getImage());
			recipeTable.setImages(this.convertImageByte(url1));
			recipeTable.setPrice(r.getPrice());
			
			recipeRepository.save(recipeTable);
		}
	}

}
