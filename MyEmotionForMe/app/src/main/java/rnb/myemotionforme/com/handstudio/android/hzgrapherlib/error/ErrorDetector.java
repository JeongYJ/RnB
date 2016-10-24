package rnb.myemotionforme.com.handstudio.android.hzgrapherlib.error;

import rnb.myemotionforme.com.handstudio.android.hzgrapherlib.vo.bubblegraph.BubbleGraphVO;
import rnb.myemotionforme.com.handstudio.android.hzgrapherlib.vo.circlegraph.CircleGraphVO;

public class ErrorDetector {

	public static ErrorCode checkGraphObject(CircleGraphVO circleGraphVO){
		//1. vo check
		if(circleGraphVO == null){
			return ErrorCode.GRAPH_VO_IS_EMPTY;
		}
		
		//2. legend and graph size check
		int arrSize = circleGraphVO.getArrGraph().size();
		if(arrSize == 0){
			return ErrorCode.GRAPH_VO_SIZE_ZERO;
		}
		
		return ErrorCode.NOT_ERROR;
	}
	
	public static ErrorCode checkGraphObject(BubbleGraphVO bubbleGraphVO){
		//1. vo check
		if(bubbleGraphVO == null){
			return ErrorCode.GRAPH_VO_IS_EMPTY;
		}
		
		//3. legend and graph size check
		int legendSize = bubbleGraphVO.getLegendArr().length;
		for (int i = 0; i < bubbleGraphVO.getArrGraph().size(); i++) {
			if(legendSize !=bubbleGraphVO.getArrGraph().get(i).getCoordinateArr().length
					|| legendSize !=bubbleGraphVO.getArrGraph().get(i).getSizeArr().length){
				return ErrorCode.INVALIDATE_GRAPH_AND_LEGEND_SIZE;
			}
		}
		
		return ErrorCode.NOT_ERROR;
	}

}
