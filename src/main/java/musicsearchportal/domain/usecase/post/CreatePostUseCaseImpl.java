package musicsearchportal.domain.usecase.post;

import com.github.f4b6a3.uuid.UuidCreator;
import musicsearchportal.boundary.model.CreatePostParam;
import musicsearchportal.boundary.model.CreatePostResult;
import musicsearchportal.boundary.usecase.CreatePostUseCase;
import org.springframework.stereotype.Service;

@Service
public class CreatePostUseCaseImpl implements CreatePostUseCase {

    @Override
    public CreatePostResult create(CreatePostParam params) {
        return new CreatePostResult(UuidCreator.getTimeOrderedEpoch().toString());
    }
}
