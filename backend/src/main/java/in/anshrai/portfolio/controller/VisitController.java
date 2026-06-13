package in.anshrai.portfolio.controller;

import in.anshrai.portfolio.dto.ApiResponse;
import in.anshrai.portfolio.dto.VisitorRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/visits")
public class VisitController {

    @PostMapping("/landing")
    public ApiResponse<Void> trackLandingVisit(@RequestBody(required = false) VisitorRequest request,
                                               HttpServletRequest httpRequest) {
        if (request != null) {
            httpRequest.setAttribute("visitorModel", request.getModel());
            httpRequest.setAttribute("visitorLatitude", request.getLatitude());
            httpRequest.setAttribute("visitorLongitude", request.getLongitude());
            httpRequest.setAttribute("visitorAccuracy", request.getAccuracy());
        }
        return ApiResponse.ok("Landing visit logged", null);
    }
}
