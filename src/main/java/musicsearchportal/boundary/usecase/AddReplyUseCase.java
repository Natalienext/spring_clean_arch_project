package musicsearchportal.boundary.usecase;

import musicsearchportal.boundary.model.AddReplyParam;
import musicsearchportal.boundary.model.AddReplyResult;

public interface AddReplyUseCase {

  AddReplyResult add(AddReplyParam param);
}
