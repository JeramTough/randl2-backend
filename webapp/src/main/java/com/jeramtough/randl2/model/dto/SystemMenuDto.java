package com.jeramtough.randl2.model.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 
 * </p>
 *
 * @author JeramTough
 * @since 2020-08-06
 */
@ApiModel(value = "SystemMenuDTO对象", description = "")
public class SystemMenuDto implements Serializable{

private static final long serialVersionUID=1L;

                @TableId(value = "fid", type = IdType.AUTO)
                private Long fid;

        private String name;

        private String description;

        private String path;


    public Long getFid(){
            return fid;
            }

        public void setFid(Long fid) {
            this.fid = fid;
            }

    public String getName(){
            return name;
            }

        public void setName(String name) {
            this.name = name;
            }

    public String getDescription(){
            return description;
            }

        public void setDescription(String description) {
            this.description = description;
            }

    public String getPath(){
            return path;
            }

        public void setPath(String path) {
            this.path = path;
            }
    
@Override
public String toString() {
        return "SystemMenu{" +
                "fid=" + fid +
                ", name=" + name +
                ", description=" + description +
                ", path=" + path +
        "}";
        }
}
