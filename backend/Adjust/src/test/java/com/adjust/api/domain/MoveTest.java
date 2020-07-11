package com.adjust.api.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.adjust.api.web.rest.TestUtil;

public class MoveTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Move.class);
        Move move1 = new Move();
        move1.setId(1L);
        Move move2 = new Move();
        move2.setId(move1.getId());
        assertThat(move1).isEqualTo(move2);
        move2.setId(2L);
        assertThat(move1).isNotEqualTo(move2);
        move1.setId(null);
        assertThat(move1).isNotEqualTo(move2);
    }
}
