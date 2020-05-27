# Instagram 캡슐 완성하기 

## 아래 순서를 따라가면서 자신만의 Instagram 캡슐을 완성하세요~

Instagram timeline 을 이용해서 간단하게 캡슐을 만들어 볼 수 있습니다. 
참고로, Instagram timeline 은 최신 순으로 12개의 아이템에 대한 정보를 제공해 줍니다. 


1.	자신의 Instagram url 로 변경하기 
  아래 파일에서 url(현재는 Instagram의 url)을 여러분이 만들고자 하는 Instagram의 url 로 변경해 주시면 됩니다. 
  마지막의 "/instagram/" 부분이 다를 것이니 참고하시기 바랍니다. 

```js
  code\getItemList.js
    let url = "https://www.instagram.com/instagram/"; // instagram url
```


2. 자신의 Instagram logo 이미지로 변경하기
  아래 파일을 자신의 Instagram logo 이미지로 변경하시기 바랍니다. 
  
 ```js
 	\assets\images\logo.jpg
 ```

 	
3.	capsule-info.bxb 파일 수정 필요
  -	\resources\ko-KR\ 폴더
  - [Create Your capsule-info.bxb File](https://bixbydevelopers.com/dev/docs/dev-guide/developers/deploying.prep-marketplace#create-your-capsule-infobxb-file) 
  - 참고하여 아래 변경 필요

 ```js
// https://bixbydevelopers.com/dev/docs/dev-guide/developers/deploying.prep-marketplace#create-your-capsule-infobxb-file
capsule-info {
  display-name (인스타그램)
  developer-name (인스타그램)
  icon-asset (images/logo.jpg)
  description (인스타그램을 볼 수 있는 캡슐입니다.)
  website-url (https://www.instagram.com/)
  privacy-policy-url () // 개인정보 처리방침 생성 -> 웹에 게시 -> url 정보 여기에 작성 필요 
  
  dispatch-name (인스타그램)
  dispatch-aliases {
    alias (인스타 그램)
    alias (인스타)
  }
  search-keywords{
    keyword (인스타그램)
    keyword (인스타) 
  }
}
 ```


4.	channel.hints.bxb 파일 수정 필요
  -	\resources\ko-KR\ 폴더 
  -	[Provide Hints for Bixby](https://bixbydevelopers.com/dev/docs/dev-guide/developers/deploying.prep-marketplace#provide-hints-for-bixby) 
	- 참고하여 각 캡슐에 맞게 변경 필요

```js
// https://bixbydevelopers.com/dev/docs/dev-guide/developers/deploying.prep-marketplace#provide-hints-for-bixby
hints {
  uncategorized {
    hint (인스타그램 연결해줘)
    hint (인스타그램 켜줘)
    hint (인스타그램 시작)
    hint (인스타그램에서 보여줘)
    hint (인스타그램에서 뭐가 있어)
  }
}
```


5.	legal.bxb 파일 수정 필요
  -	캡슐 root 폴더 
  -	[Provide Legal Agreements](https://bixbydevelopers.com/dev/docs/dev-guide/developers/deploying.prep-marketplace#provide-legal-agreements)
  - [Privacy Policy Guidelines (KR)](https://bixbydevelopers.com/dev/docs/dev-guide/developers/deploying.privacy-policy-kr)
  -	참고하여 아래 변경 필요

```js
// https://bixbydevelopers.com/dev/docs/dev-guide/developers/deploying.prep-marketplace#provide-legal-agreements
legal {
  country-documents {
    store-country (KR)
    privacy-policy { // 개인정보 처리방침
      url() // 개인정보 처리방침 생성 -> 웹에 게시 -> url 정보 여기에 작성 필요 
    }    
  }
}
```
 
6.	MarketPlace submit 하기
-	[Working with the Marketplace](https://bixbydevelopers.com/dev/docs/dev-guide/developers/deploying.can-submission) 
-	참고하여 진행 

```js
Public Submission
-	Bixby Studio 에서 Submissions 버튼을 통해 진행
-	Create New Submission
-	Public 탭 선택 
-	Submission 하고자 하는 캡슐 선택 
-	Description 작성 : 버전 정보 및 수정 내역 간단하게 작성
-	Submit Capsules 버튼 눌러 submit 진행
```


---

## Additional Resources

### Your Source for Everything Bixby

* [Bixby Developer Center](https://bixbydevelopers.com) - Everything you need to get started with Bixby Development!

### Guides & Best Practices

* [Quick Start Guide](https://bixbydevelopers.com/dev/docs/get-started/quick-start) - Build your first capsule
* [Design Guides](https://bixbydevelopers.com/dev/docs/dev-guide/design-guides) - Best practices for designing your capsules
* [Developer Guides](https://bixbydevelopers.com/dev/docs/dev-guide/developers) - Guides that take you from design and modeling all the way through deployment of your capsules

### Video Guides

* [Introduction to Bixby](https://youtu.be/DFvpK4PosvI) - Bixby and the New Exponential Frontier of Intelligent Assistants
* [Bixby Fundamentals](https://bixby.developer.samsung.com/newsroom/en-us/22/01/2019/Teaching-Bixby-Fundamentals-What-You-Need-to-Know) - Bixby Fundamentals: What You Need to Know

### Need Support?

* Have a feature request? Please suggest it in our [Support Community](https://support.bixbydevelopers.com/hc/en-us/community/topics/360000183273-Feature-Requests) to help us prioritize.
* Have a technical question? Ask on [Stack Overflow](https://stackoverflow.com/questions/tagged/bixby) with tag ��bixby��
