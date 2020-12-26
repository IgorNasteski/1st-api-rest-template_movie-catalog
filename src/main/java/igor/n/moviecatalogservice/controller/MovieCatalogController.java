package igor.n.moviecatalogservice.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import igor.n.moviecatalogservice.model.CatalogItem;
import igor.n.moviecatalogservice.model.Movie;
import igor.n.moviecatalogservice.model.Rating;
import igor.n.moviecatalogservice.model.RatingResponse;
import igor.n.moviecatalogservice.service.MovieInfo;
import igor.n.moviecatalogservice.service.UserRatingInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/movieCatalog")
public class MovieCatalogController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private MovieInfo movieInfo;

    @Autowired
    private UserRatingInfo userRatingInfo;

    @RequestMapping("/{userId}")                        //ZBOG KORISCENJA EUREKE MENJAM IZ http://localhost:8083/ratings/users/ U http://rating-data-service/ratings/users/
                         //u ovoj metodi moze doci do "circuit breakers-a" (da servis/api sa kojim komuniciramo je nedostupan)-objasnjeno u folderu "dokumentacija"
    public List<CatalogItem> getCatalog(@PathVariable("userId")String userId){
        RatingResponse ratings = userRatingInfo.getUserRating(userId);

        List<CatalogItem> catalogItems = new ArrayList<>();

        for(Rating rating : ratings.getRatings()){      //ZBOG KORISCENJA EUREKE MENJAM IZ http://localhost:8082/movies/ U http://movie-info-service/movies/
            //For each movie ID, call movie info service and get details
            catalogItems.add(movieInfo.getCatalogItem(rating));
        }

        return catalogItems;
    }








}
