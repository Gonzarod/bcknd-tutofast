package com.evertix.tutofastbackend.controller;

import com.evertix.tutofastbackend.model.Review;
import com.evertix.tutofastbackend.model.Subscription;
import com.evertix.tutofastbackend.resource.PlanSaveResource;
import com.evertix.tutofastbackend.resource.ReviewResource;
import com.evertix.tutofastbackend.resource.ReviewSaveResource;
import com.evertix.tutofastbackend.resource.SubscriptionResource;
import com.evertix.tutofastbackend.service.ReviewService;
import com.evertix.tutofastbackend.service.SubscriptionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Review", description = "API")
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class ReviewController {
    @Autowired
    private ModelMapper mapper;

    @Autowired
    ReviewService reviewService;

    @GetMapping("/reviews")
    @Operation(summary = "Get All Reviews", description = "Get All Review", tags = {"Review"},
            parameters = {
                    @Parameter(in = ParameterIn.QUERY
                            , description = "Page you want to retrieve (0..N)"
                            , name = "page"
                            , content = @Content(schema = @Schema(type = "integer", defaultValue = "0"))),
                    @Parameter(in = ParameterIn.QUERY
                            , description = "Number of records per page."
                            , name = "size"
                            , content = @Content(schema = @Schema(type = "integer", defaultValue = "20"))),
                    @Parameter(in = ParameterIn.QUERY
                            , description = "Sorting criteria in the format: property(,asc|desc). "
                            + "Default sort order is ascending. " + "Multiple sort criteria are supported."
                            , name = "sort"
                            , content = @Content(array = @ArraySchema(schema = @Schema(type = "string"))))
            })
    public Page<ReviewResource> getAllReviews(@PageableDefault @Parameter(hidden = true) Pageable pageable){
        Page<Review> reviewPage = reviewService.getAllReview(pageable);
        List<ReviewResource> resources = reviewPage.getContent().stream().map(this::convertToResource).collect(Collectors.toList());
        return new PageImpl<>(resources,pageable,reviewPage.getTotalElements());
    }

    @GetMapping("reviews/teacher/{teacherId}")
    @Operation(summary = "Get Reviews By User Id", description = "Get Reviews By Teacher Id", tags = {"Review"})
    public Page<ReviewResource> getAllTeachersReviews(@PathVariable(name = "teacherId") Long teacherId, @PageableDefault @Parameter(hidden = true) Pageable pageable){
        Page<Review> reviewPage = reviewService.getReviewsByTeacher(teacherId, pageable);
        List<ReviewResource> resources = reviewPage.getContent().stream().map(this::convertToResource).collect(Collectors.toList());
        return new PageImpl<>(resources,pageable,resources.size());
    }

    @PostMapping("/reviews/student/{studentId}/teacher/{teacherId}")
    @Operation(summary = "Create To Review", description = "Student creates a Review of a Teacher", tags = {"Review"})
    public ReviewResource subscribeToPlan(@PathVariable(name = "studentId") Long studentId,@PathVariable(name = "teacherId") Long teacherId,@Valid @RequestBody ReviewSaveResource resource){
        return convertToResource(reviewService.createReview(studentId,teacherId,convertToEntity(resource)));
    }

    @PutMapping("/reviews/{reviewId}")
    @Operation(summary = "Create To Review", description = "Student creates a Review of a Teacher", tags = {"Review"})
    public ReviewResource updateReviewDetails(@PathVariable(name = "reviewId") Long reviewId,@Valid @RequestBody ReviewSaveResource resource){
        return convertToResource(reviewService.updateReview(reviewId,convertToEntity(resource)));
    }

    @DeleteMapping("/reviews/{reviewId}")
    @Operation(summary = "Delete Review", description = "Student delete Review", tags = {"Review"})
    public ResponseEntity<?> updateReview(@PathVariable(name = "reviewId") Long reviewId){
        return reviewService.deleteReview(reviewId);
    }


    private Review convertToEntity(ReviewSaveResource resource) {return mapper.map(resource,Review.class);}
    private ReviewResource convertToResource(Review entity){return mapper.map(entity, ReviewResource.class);}
}
