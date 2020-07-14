package com.adjust.api.service.dto;

import java.io.Serializable;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.adjust.api.domain.TutorialVideo} entity.
 */
public class TutorialVideoDTO implements Serializable {
    
    private Long id;

    private Long adjustTutorialVideoId;

    @Lob
    private byte[] content;

    private String contentContentType;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAdjustTutorialVideoId() {
        return adjustTutorialVideoId;
    }

    public void setAdjustTutorialVideoId(Long adjustTutorialVideoId) {
        this.adjustTutorialVideoId = adjustTutorialVideoId;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public String getContentContentType() {
        return contentContentType;
    }

    public void setContentContentType(String contentContentType) {
        this.contentContentType = contentContentType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TutorialVideoDTO)) {
            return false;
        }

        return id != null && id.equals(((TutorialVideoDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TutorialVideoDTO{" +
            "id=" + getId() +
            ", adjustTutorialVideoId=" + getAdjustTutorialVideoId() +
            ", content='" + getContent() + "'" +
            "}";
    }
}
