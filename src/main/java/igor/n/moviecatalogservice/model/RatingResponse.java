package igor.n.moviecatalogservice.model;

import java.util.List;

public class RatingResponse {

    private String id;
    private List<Rating> ratings;

    public RatingResponse(){}

    public RatingResponse(String id, List<Rating> ratings) {
        this.id = id;
        this.ratings = ratings;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public List<Rating> getRatings() {
        return ratings;
    }
    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }
}
