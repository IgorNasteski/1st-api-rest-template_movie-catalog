package igor.n.moviecatalogservice.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import igor.n.moviecatalogservice.model.Rating;
import igor.n.moviecatalogservice.model.RatingResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Service
public class UserRatingInfo {

    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "getFallbackUserRating",
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000")
            })
    public RatingResponse getUserRating(@PathVariable("userId") String userId) {
        return restTemplate.getForObject("http://rating-data-service/ratings/users/" + userId, RatingResponse.class);
    }

    //OVO JE FALLBACK METHOD ZA METOD IZNAD, AKO DODJE DO BREAK-A SA TIM SERVISOM SA KOJIM KOMUNICIRAM, OVAJ HARDCODIRAN ODGOVOR METODE CE SE VRATITI KORISNIKU
    public RatingResponse getFallbackUserRating(@PathVariable("userId") String userId) {
        RatingResponse ratingResponse = new RatingResponse();
        ratingResponse.setId(userId);
        ratingResponse.setRatings(Arrays.asList(
                new Rating("0", 0)
        ));
        return ratingResponse;
    }

}
