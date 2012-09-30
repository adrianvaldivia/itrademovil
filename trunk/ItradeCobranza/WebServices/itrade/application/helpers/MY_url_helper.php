<?php
/**
 * @param    string   identical to the 1st param of anchor()
 * @param    mixed    identical to the 3rd param of anchor()
 * @param    string   the path to the image; it can be either an external one 
 *                    starting by "http://", or internal to your application
 * @param    mixed    image attributes that have similar structure as the 3rd param of anchor()
 * @return   string
 * 
 * Example 1: anchor_img('controller/method', 'title="My title"', 'path/to/the/image.jpg', 'alt="My image"')
 * Example 2: anchor_img('http://example.com', array('title' => 'My title'), 'http://example.com/image.jpg', array('alt' => 'My image'))
 */

function anchor_img($uri = '', $anchor_attributes = '', $img_src = '', $img_attributes = '', $img_text = '')
{		
    if ( ! is_array($uri))
    {
        $site_url = ( ! preg_match('!^\w+://! i', $uri)) ? site_url($uri) : $uri;
    }
    else
    {
        $site_url = site_url($uri);
    }
	
    if ($anchor_attributes != '')
    {
        $anchor_attributes = _parse_attributes($anchor_attributes);
    }
    
    if (strpos($img_src, '://') === FALSE)
    {
        $CI =& get_instance();
        $img_src = $CI->config->slash_item('base_url').$img_src;
    }
    
    if ($img_attributes != '')
    {
        $img_attributes = _parse_attributes($img_attributes);
    }
	
    if ($img_text != '')
    {
        $img_text = '<span>'.$img_text.'</span>';
    }
    return '<a href="'.$site_url.'" '.$anchor_attributes.'>'.'<img src="'.$img_src.'" />'.$img_text.'</a>';
}

?>
