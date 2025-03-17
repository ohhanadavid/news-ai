from dataclasses import dataclass
from typing import List, Dict, Optional
import json




@dataclass
class DataForNews:
    number_of_article: Optional[int] = None
    to: Optional[str] = None
    option: Optional[str] = None

    def set_data(self, data: 'DataForNews') -> 'DataForNews':
        self.number_of_article = data.number_of_article
        self.to = data.to
        self.option=data.option
        return self

    @classmethod
    def from_json(cls, json_data: dict) -> 'DataForNews':
        return cls(
            number_of_article=json_data.get('number_of_article'),
            to=json_data.get('to'),
            option=json_data.get('option')

        )


@dataclass
class ArticleToGetFilter:
    url: str
    title: str
    summary: str = ""
    video_url: Optional[str] = None
    pub_date: Optional[str] = None
    description: Optional[str] = None

    @classmethod
    def from_article(cls, article):
        return cls(
            url=article.link,
            title=article.title,
            video_url=article.video_url,
            summary="",
            pub_date=article.pub_date,
            description=article.description
        )

    @classmethod
    def from_json(cls, json_data: dict) -> 'ArticleToGetFilter':
        return cls(
            url=json_data['url'],
            title=json_data['title'],
            summary=json_data.get('summary', ""),
            video_url=json_data.get('video_url'),
            pub_date=json_data.get('pubDate'),
            description=json_data.get('description')
        )

    def __str__(self) -> str:
        return f"-  url: {self.url}\n" \
               f"title: {self.title}\n" \
               f"   summary: {self.summary}\n" \
               f"   video url: {self.video_url}\n" \
               f"   publish date: {self.pub_date}\n"


@dataclass
class ArticlesToFilter(DataForNews):
    article_return_list: List[ArticleToGetFilter] = None
    preference: Dict[str, List[str]] = None



    def __post_init__(self):
        if self.article_return_list is None:
            self.article_return_list = []
        if self.preference is None:
            self.preference = {}

    def set_data(self, data: DataForNews) -> 'ArticlesToFilter':
        super().set_data(data)
        return self

    @classmethod
    def from_json(cls, json_string: str) -> 'ArticlesToFilter':
        """Convert JSON string to ArticlesToFilter instance"""
        json_data = json.loads(json_string)

        # Convert article list from JSON
        articles = []
        if json_data.get('articleReturnList'):
            articles = [ArticleToGetFilter.from_json(article) for article in json_data['articleReturnList']]

        # Create instance
        return cls(
            number_of_article=json_data.get('numberOfArticle'),
            to=json_data.get('to'),
            option=json_data.get('option'),
            article_return_list=articles,
            preference=json_data.get('preference', {})
        )

    def to_json(self) -> str:
        """Convert ArticlesToFilter instance to JSON string"""
        return json.dumps({
            'numberOfArticle': self.number_of_article,
            'to': self.to,
            'option': self.option,
            'articleReturnList': [
                {
                    'url': article.url,
                    'title': article.title,
                    'summary': article.summary,
                    'videoUrl': article.video_url,
                    'pubDate': article.pub_date,
                    'description': article.description
                }
                for article in self.article_return_list
            ],
            'preference': self.preference
        })