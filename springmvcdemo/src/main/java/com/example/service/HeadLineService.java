package com.example.service;

import com.example.entity.bo.HeadLine;
import com.example.entity.dto.Result;

public interface HeadLineService {
	Result<Boolean> addHeadLine(HeadLine headLine);
}
