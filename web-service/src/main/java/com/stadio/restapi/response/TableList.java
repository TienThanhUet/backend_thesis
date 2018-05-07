package com.stadio.restapi.response;

import com.stadio.restapi.model.PageInfo;
import lombok.Data;

import java.util.List;

/**
 * Created by Andy on 11/21/2017.
 */
@Data
public class TableList<DTO>
{
    private PageInfo pageInfo;
    private List<DTO> items;

    public TableList(PageInfo page, List<DTO> items)
    {
        this.pageInfo = page;
        this.items = items;
    }
}
