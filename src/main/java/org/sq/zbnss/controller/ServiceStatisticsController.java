package org.sq.zbnss.controller;

import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "业务驾驶舱", tags = "业务驾驶舱")
@RestController
@RequestMapping("/api/System")
@AllArgsConstructor
public class ServiceStatisticsController {
}
