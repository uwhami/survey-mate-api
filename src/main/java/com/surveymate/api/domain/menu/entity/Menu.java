package com.surveymate.api.domain.menu.entity;

import com.surveymate.api.common.converter.MemberRoleConverter;
import com.surveymate.api.common.entity.BaseEntity;
import com.surveymate.api.common.enums.MemberRole;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@Table(name = "menu")
public class Menu extends BaseEntity {
    @Id
    @Column(name = "menu_no", nullable = false, length = 14)
    private String menuNo;

    @Column(name = "parent_menu_no", length = 14)
    private String  parentMenuNo;

    @Column(name = "menu_kor_name", length = 50, nullable = false)
    private String menuKorName;

    @Column(name = "menu_eng_name", length = 50, nullable = false)
    private String menuEngName;

    @Column(name = "menu_description", length = 255, nullable = false)
    private String menuDescription;

    @Column(name = "menu_path", length = 100, nullable = false)
    private String menuPath;

    @Convert(converter = MemberRoleConverter.class)
    @Column(name = "mem_role", length = 1, nullable = false)
    @Comment("0:ROLE_USER, 1:ROLE_MANAGER, 2:ROLE_ADMIN")
    private MemberRole memRole;

    @Column(name = "sequence", nullable = false)
    private int sequence; // 메뉴 순서

    @Column(name = "use_yn", length = 1, nullable = false)
    private String useYn;

    // 자식 메뉴를 수동으로 가져오는 편의 메서드
    @Transient
    private List<Menu> subMenus = new ArrayList<>();

    public void addChildMenu(Menu child) {
        this.subMenus.add(child);
    }

    // Builder를 사용하되, Auditing 필드 제외
    @Builder
    public Menu(String menuNo, String parentMenuNo, String menuKorName, String menuEngName,
                String menuDescription, String menuPath, MemberRole memRole,
                int sequence, String useYn) {
        this.menuNo = menuNo;
        this.parentMenuNo = parentMenuNo;
        this.menuKorName = menuKorName;
        this.menuEngName = menuEngName;
        this.menuDescription = menuDescription;
        this.menuPath = menuPath;
        this.memRole = memRole;
        this.sequence = sequence;
        this.useYn = useYn;
    }
}
