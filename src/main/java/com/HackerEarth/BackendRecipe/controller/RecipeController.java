package com.HackerEarth.BackendRecipe.controller;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.HackerEarth.BackendRecipe.Dao.RecipeRepository;
import com.HackerEarth.BackendRecipe.Model.RecipeTable;
import com.HackerEarth.BackendRecipe.service.RecipeService;

@RestController
public class RecipeController {

	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private RecipeRepository recipeRepository;
	@Autowired
	private RecipeService recipeService;
	
	@GetMapping("/testing")
	public String getTesting() {
		return "Working";
	}
	
//	@GetMapping("/recipe")
//	public List<Object> getRecipe(){
//		String url="https://s3-ap-southeast-1.amazonaws.com/he-public-data/reciped9d7b8c.json";
//		Object[] objects=restTemplate.getForObject(url,Object[].class);
//		return Arrays.asList(objects);
//	} 
//	
	
// call another rest api from my server and save into the database in spring-boot 
	
//	@GetMapping("/recipe")
//	public List<RecipeTable> getRecipe(){
//		String url="https://s3-ap-southeast-1.amazonaws.com/he-public-data/reciped9d7b8c.json";
//		RecipeTable[] objects=restTemplate.getForObject(url,RecipeTable[].class);
//		List<RecipeTable> res=Arrays.asList(objects);
//		for(RecipeTable r:res) {
//			RecipeTable recipeTable=new RecipeTable();
//			recipeTable.setId(r.getId());
//			recipeTable.setName(r.getName());
//			recipeTable.setCategory(r.getCategory());
//			recipeTable.setDescription(r.getDescription());
//			recipeTable.setLabel(r.getLabel());
//			recipeTable.setImage(r.getImage());
//			recipeTable.setPrice(r.getPrice());
//			recipeRepository.save(recipeTable);
//		}
//		return res;
//	} 
	
	@GetMapping("/")
	public List<RecipeTable> getAllRecipe(){
		return this.recipeService.getAllRecipe();
	}
	
	@GetMapping("/{recipeId}")
	public RecipeTable getRecipe(@PathVariable String recipeId) {
		return recipeService.getRecipeService(Integer.parseInt(recipeId));
	}
	
	@PostMapping("/")
	public ResponseEntity<HttpStatus> AddRecipe(@RequestBody RecipeTable recipe){
		try {
			this.recipeService.AddRecipeService(recipe);
			return new ResponseEntity<>(HttpStatus.ACCEPTED);
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
//	@RequestMapping(value = "/{recipeId}/show",method = RequestMethod.GET,consumes = MediaType.IMAGE_JPEG_VALUE)
//	public ResponseEntity<byte[]> getImage(@PathVariable String recipeId,HttpServletResponse response){
//		this.recipeService.getRecipeService(Integer.parseInt(recipeId));
//		byte[] img=ImageIO.getImage
//		return null;
//		
//	}
	
	
	
	@RequestMapping(value = "/{recipeId}/show",method = RequestMethod.GET)
	public HttpEntity<byte[]> getImage(@PathVariable String recipeId) throws IOException{
		RecipeTable recipe=recipeService.getRecipeService(Integer.parseInt(recipeId));
		URL url = new URL(recipe.getImage());
		InputStream in = new BufferedInputStream(url.openStream());
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] buf = new byte[1024];
		int n = 0;
		while (-1!=(n=in.read(buf)))
		{
		   out.write(buf, 0, n);
		}
		out.close();
		in.close();
		byte[] response = out.toByteArray();
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.IMAGE_JPEG); 
	    headers.setContentLength(response.length);

	    return new HttpEntity<byte[]>(response, headers);
	}

}
