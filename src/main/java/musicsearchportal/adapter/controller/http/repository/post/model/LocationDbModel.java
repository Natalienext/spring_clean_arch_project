package musicsearchportal.adapter.controller.http.repository.post.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LocationDbModel {

  private String city;
  private String district;
  private Boolean remoteOk;
}
