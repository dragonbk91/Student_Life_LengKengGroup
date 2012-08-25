package lengkeng.group.Student;

import org.anddev.andengine.entity.IEntity;
import org.anddev.andengine.entity.modifier.IEntityModifier;
import org.anddev.andengine.util.modifier.BaseModifier;
import org.anddev.andengine.util.modifier.ease.EaseLinear;
import org.anddev.andengine.util.modifier.ease.IEaseFunction;
/**
 * @author DRAGON
 * @version 1.0
 * @created 15-Thg7-2012 4:07:28 CH
 */
public class ReuseMoveModifier extends BaseModifier<IEntity> implements IEntityModifier
{
   private float mDuration;
   private float mSecondsElapsed;
   private float mFromX;
   private float mFromY;
   private float mToX;
   private float mToY;
   private float mSpanX;
   private float mSpanY;
 
   private IEaseFunction mEaseFunction;
 
   public ReuseMoveModifier(final IEntityModifierListener pEntityModifierListener)
   {
      this(0, 0, 0, 0, 0, pEntityModifierListener, EaseLinear.getInstance());
   }
 
   public ReuseMoveModifier(final float pDuration,
                            final IEaseFunction pEaseFunction)
   {
      this(pDuration, 0, 0, 0, 0, null, pEaseFunction);
   }
 
   public ReuseMoveModifier(final IEntityModifierListener pEntityModifierListener,
                            final IEaseFunction pEaseFunction)
   {
      this(0, 0, 0, 0, 0, pEntityModifierListener, pEaseFunction);
   }
 
   public ReuseMoveModifier(final float pDuration,
                            final IEntityModifierListener pEntityModifierListener,
                            final IEaseFunction pEaseFunction)
   {
      this(pDuration, 0, 0, 0, 0, pEntityModifierListener, pEaseFunction);
   }
 
   public ReuseMoveModifier(final float pDuration,
                            final float pToX,
                            final float pToY,
                            final IEntityModifierListener pEntityModifierListener,
                            final IEaseFunction pEaseFunction)
   {
      this(pDuration, 0, pToX, 0, pToY, pEntityModifierListener, pEaseFunction);
   }
 
   public ReuseMoveModifier(final float pDuration,
                            final float pFromX, final float pToX,
                            final float pFromY, final float pToY,
                            final IEaseFunction pEaseFunction)
   {
      this(pDuration, pFromX, pToX, pFromY, pToY, null, pEaseFunction);
   }
 
   public ReuseMoveModifier(final float pDuration,
                            final float pFromX, final float pToX,
                            final float pFromY, final float pToY,
                            final IEntityModifierListener pEntityModifierListener,
                            final IEaseFunction pEaseFunction)
   {
      super(pEntityModifierListener);
 
      mDuration = pDuration;
      mSecondsElapsed = 0;
      mFromX = pFromX;
      mFromY = pFromY;
      mToX = pToX;
      mToY = pToY;
      mSpanX = pToX - pFromX;
      mSpanY = pToY - pFromY;
      mEaseFunction = pEaseFunction;
      mFinished = true;
 
      setRemoveWhenFinished(false);
   }
 
   private ReuseMoveModifier(final ReuseMoveModifier pReuseMoveModifier)
   {
      mDuration = pReuseMoveModifier.mDuration;
      mSecondsElapsed = 0;
      mFromX = pReuseMoveModifier.mFromX;
      mFromY = pReuseMoveModifier.mFromY;
      mToX = pReuseMoveModifier.mToX;
      mToY = pReuseMoveModifier.mToY;
      mSpanX = pReuseMoveModifier.mSpanX;
      mSpanY = pReuseMoveModifier.mSpanY;
      mEaseFunction = pReuseMoveModifier.mEaseFunction;
      mFinished = pReuseMoveModifier.mFinished;
 
      setRemoveWhenFinished(false);
   }
 
   @Override
   public void reset()
   {
      mFinished = false;
      mSecondsElapsed = 0;
   }
 
   @Override
   public float getSecondsElapsed()
   {
      return mSecondsElapsed;
   }
 
   @Override
   public float getDuration()
   {
      return mDuration;
   }
 
   @Override
   public float onUpdate(final float pSecondsElapsed, final IEntity pItem)
   {
      if (mFinished)
      {
         return 0;
      }
      else
      {
         if (mSecondsElapsed == 0)
         {
            onManagedInitialize(pItem);
            onModifierStarted(pItem);
         }
 
         final float secondsElapsedUsed;
         if (mSecondsElapsed + pSecondsElapsed < mDuration)
         {
            secondsElapsedUsed = pSecondsElapsed;
         }
         else
         {
            secondsElapsedUsed = mDuration - mSecondsElapsed;
         }
 
         mSecondsElapsed += secondsElapsedUsed;
         onManagedUpdate(secondsElapsedUsed, pItem);
 
         if (mSecondsElapsed >= mDuration)
         {
            mSecondsElapsed = mDuration;
            mFinished = true;
            onModifierFinished(pItem);
         }
 
         return secondsElapsedUsed;
      }
   }
 
   @Override
   public ReuseMoveModifier deepCopy()
   {
      return new ReuseMoveModifier(this);
   }
 
   public void restart()
   {
      reset();
   }
 
   public void restartFrom(final float pFromX,
                           final float pFromY)
   {
      reset();
      mFromX = pFromX;
      mFromY = pFromY;
      mSpanX = mToX - mFromX;
      mSpanY = mToY - mFromY;
   }
 
   public void restartTo(final float pToX,
                         final float pToY)
   {
      reset();
      mToX = pToX;
      mToY = pToY;
      mSpanX = pToX - mFromX;
      mSpanY = pToY - mFromY;
   }
 
   public void restart(final float pFromX, final float pToX,
                       final float pFromY, final float pToY)
   {
      reset();
      mFromX = pFromX;
      mFromY = pFromY;
      mToX = pToX;
      mToY = pToY;
      mSpanX = pToX - pFromX;
      mSpanY = pToY - pFromY;
   }
 
   public void restart(final float pDuration,
                       final float pFromX, final float pToX,
                       final float pFromY, final float pToY)
   {
      restart(pFromX, pToX, pFromY, pToY);
      mDuration = pDuration;
   }
 
   public void restart(final float pDuration,
                       final float pFromX, final float pToX,
                       final float pFromY, final float pToY,
                       final IEaseFunction pEaseFunction)
   {
      restart(pDuration, pFromX, pToX, pFromY, pToY);
      mEaseFunction = pEaseFunction;
   }
 
   private void onManagedUpdate(final float pSecondsElapsed, final IEntity pEntity)
   {
      final float percentageDone = mEaseFunction.getPercentage(getSecondsElapsed(), mDuration);
 
      pEntity.setPosition(mFromX + percentageDone * mSpanX, mFromY + percentageDone * mSpanY);
   }
 
   private void onManagedInitialize(final IEntity pEntity)
   {
      pEntity.setPosition(mFromX, mFromY);
   }
 
}


