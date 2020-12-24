package igor.n.moviecatalogservice.controller;

import igor.n.moviecatalogservice.model.CatalogItem;
import igor.n.moviecatalogservice.model.Movie;
import igor.n.moviecatalogservice.model.Rating;
import igor.n.moviecatalogservice.model.RatingResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/movieCatalog")
public class MovieCatalogController {

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/{userId}")                        //ZBOG KORISCENJA EUREKE MENJAM IZ http://localhost:8083/ratings/users/ U http://rating-data-service/ratings/users/
    public List<CatalogItem> getCatalogItems(@PathVariable("userId")String userId){
        RatingResponse ratings = restTemplate.getForObject("http://rating-data-service/ratings/users/" + userId, RatingResponse.class);

        List<CatalogItem> catalogItems = new ArrayList<>();

        for(Rating rating : ratings.getRatings()){      //ZBOG KORISCENJA EUREKE MENJAM IZ http://localhost:8082/movies/ U http://movie-info-service/movies/
            //For each movie ID, call movie info service and get details
            Movie movie = restTemplate.getForObject("http://movie-info-service/movies/" + rating.getMovieId(), Movie.class);
            //Put them all together
            catalogItems.add(new CatalogItem(movie.getName(), movie.getDescription(), rating.getRating()));
        }

        return catalogItems;
    }

}
