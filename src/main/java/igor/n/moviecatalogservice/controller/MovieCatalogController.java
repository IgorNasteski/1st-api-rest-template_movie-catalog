package igor.n.moviecatalogservice.controller;

import igor.n.moviecatalogservice.model.CatalogItem;
import igor.n.moviecatalogservice.model.Movie;
import igor.n.moviecatalogservice.model.Rating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/movieCatalog")
public class MovieCatalogController {

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/{userId}")
    public CatalogItem getCatalogItems(@PathVariable("userId")String userId){
        Rating rating = restTemplate.getForObject("http://localhost:8083/ratings/" + userId, Rating.class);
        Movie movie = restTemplate.getForObject("http://localhost:8082/movies/" + rating.getMovieId(), Movie.class);

        CatalogItem catalogItem = new CatalogItem(movie.getName(), "opis zakucan", rating.getRating());

        return catalogItem;
    }

}
