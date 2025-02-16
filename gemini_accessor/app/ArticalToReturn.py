import json
from dataclasses import dataclass
from typing import  Optional


@dataclass
class ArticleReturn:
    url: str
    title: str
    summary: str = ""
    videoUrl: Optional[str] = None
    pubDate: Optional[str] = None


    @classmethod
    def from_article(cls, article):
        return cls(
            url=article.link,
            title=article.title,
            videoUrl=article.video_url,
            summary="",
            pubDate=article.pub_date

        )

    @classmethod
    def from_json(cls, json_data: dict) -> 'ArticleReturn':
        return cls(
            url=json_data['url'],
            title=json_data['title'],
            summary=json_data.get('summary', ""),
            videoUrl=json_data.get('videoUrl'),
            pubDate=json_data.get('pubDate')

        )

    def __str__(self) -> str:
        return f"-  url: {self.url}\n" \
               f"title: {self.title}\n" \
               f"   summary: {self.summary}\n" \
               f"   video url: {self.videoUrl}\n" \
               f"   publish date: {self.pubDate}\n"



@dataclass
class DataToReturn:
    to:str
    articles:list[ArticleReturn]

    @classmethod
    def from_json(cls, json_data: dict) -> 'DataToReturn':
        articals=json.loads(json_data['articles'])
        return cls(
            to=json_data['to'],
            articles=[ArticleReturn.from_json(article) for article in articals]
            #articles=[ArticleReturn.from_json(article) for article in json_data['articles']]

        )

